package com.shallop.bpc.collection.serviceframework.dataloader;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.shallop.bpc.collection.utils.Printer.pt;

/** N+1问题lambda解决
 * @author StickChen
 * @date 2019/1/6
 */
public class DataLoaderDemo {

	public List<Loan> queryLoans() {
		return Arrays.asList(new Loan("L1"), new Loan("L2"));
	}

	public List<Plan> queryPlans(List<String> loanIds) {
		return Arrays.asList(new Plan("P1", "L1"), new Plan("P2", "L1"), new Plan("P3", "L2"), new Plan("P4", "L2"));
	}

	@Test
	public void testDataLoaderDemo() {

		List<Loan> loans = queryLoans();

        Function<Loan, String> getId = Loan::getId;
        List<String> loanIds = loans.stream().map(Loan::getId).collect(Collectors.toList());
        List<Plan> plans = queryPlans(loanIds);
        Map<String, Loan> loanMap = loans.stream().collect(Collectors.toMap(getId, loan -> loan));
        plans.forEach(plan -> {
            Loan loan = loanMap.get(plan.getLoanId());
            if (loan != null) {
                List<Plan> loanPlans = loan.getPlans();
                if (loanPlans == null) {
                    loanPlans = new ArrayList<>();
                    loan.setPlans(loanPlans);
                }
                loanPlans.add(plan);
            }
        });

	}
	
	@Test
	public void testDataLoaderDemo1(){
        List<Loan> loans = queryLoans();
        dataLoader(this, DataLoaderDemo::queryPlans, loans, Loan::getId, Plan::getLoanId, Loan::getPlans, Loan::setPlans);
        pt(JSON.toJSONString(loans));
    }

	private <P, C, M, T> void dataLoader(M m, BiFunction<M, List<T>, List<C>> queryChildren, List<P> parents,
			Function<P, T> getParentId, Function<C, T> childGetParentId, Function<P, List<C>> parentGetChildren,
			BiFunction<P, List<C>, P> parentSetChildren) {
        List<T> parentIds = parents.stream().map(getParentId).collect(Collectors.toList());
        List<C> children = queryChildren.apply(m, parentIds);
        Map<T, P> parentMap = parents.stream().collect(Collectors.toMap(getParentId, parent -> parent));
        children.forEach(child -> {
            T parentId = childGetParentId.apply(child);
            P parent = parentMap.get(parentId);
            if (parent != null) {
                List<C> parentChildren = parentGetChildren.apply(parent);
                if (parentChildren == null) {
                    parentChildren = new ArrayList<>();
                    parentSetChildren.apply(parent, parentChildren);
                }
                parentChildren.add(child);
            }
        });
    }

    public class Loan {
		private String id;
		private List<Plan> plans;

		public List<Plan> getPlans() {
			return plans;
		}

		public Loan setPlans(List<Plan> plans) {
			this.plans = plans;
			return this;
		}

		public String getId() {
			return id;
		}

		public Loan setId(String id) {
			this.id = id;
			return this;
		}

		public Loan(String id) {
			this.id = id;
		}
	}

	public class Plan {
		private String id;
		private String loanId;

		public Plan(String id, String loanId) {
			this.id = id;
			this.loanId = loanId;
		}

		public String getId() {
			return id;
		}

		public Plan setId(String id) {
			this.id = id;
			return this;
		}

		public String getLoanId() {
			return loanId;
		}

		public Plan setLoanId(String loanId) {
			this.loanId = loanId;
			return this;
		}
	}
}
