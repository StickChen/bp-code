package com.shallop.bpc.collection.basic.jvm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author chenxuanlong
 * @date 2017/3/30
 */
public class TestTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classFileBuffer) throws IllegalClassFormatException {
		ClassReader cr = new ClassReader(classFileBuffer);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		for (Object obj : cn.methods) {
			MethodNode md = (MethodNode) obj;
			if ("<init>".endsWith(md.name) || "<clinit>".equals(md.name)) {
				continue;
			}
			InsnList insns = md.instructions;
			InsnList il = new InsnList();
			il.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
			il.add(new LdcInsnNode("Enter method-> " + cn.name + "." + md.name));
			il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"));
			insns.insert(il);
			md.maxStack += 3;
		}
		ClassWriter cw = new ClassWriter(0);
		cn.accept(cw);
		return cw.toByteArray();
	}

}
