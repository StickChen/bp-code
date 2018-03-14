package com.shallop.bpc.collection.persistent;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;
import org.junit.Test;

import java.util.Date;

/**
 * @author chenxuanlong
 * @date 2018/3/9
 */
public class SqlBuilderDemo {

    @Test
    public void testSql() {
        // create default schema
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        // add table with basic customer info
        DbTable customerTable = schema.addTable("customer");
        DbColumn custIdCol = customerTable.addColumn("cust_id", "number", null);
        DbColumn custNameCol = customerTable.addColumn("name", "varchar", 255);

        // add order table with basic order info
        DbTable orderTable = schema.addTable("order");
        DbColumn orderIdCol = orderTable.addColumn("order_id", "number", null);
        DbColumn orderCustIdCol = orderTable.addColumn("cust_id", "number", null);
        DbColumn orderTotalCol = orderTable.addColumn("total", "number", null);
        DbColumn orderDateCol = orderTable.addColumn("order_date", "timestamp", null);

        // add a join from the customer table to the order table (on cust_id)
        DbJoin custOrderJoin = spec.addJoin(null, "customer", null, "order", "cust_id");

        String createCustomerTable = new CreateTableQuery(customerTable, true).validate().toString();
        System.out.println(createCustomerTable);

        // => CREATE TABLE customer (cust_id number,name varchar(255))

        String createOrderTable = new CreateTableQuery(orderTable, true).validate().toString();
        System.out.println(createOrderTable);

        String insertCustomerQuery = new InsertQuery(customerTable).addColumn(custIdCol, 1).addColumn(custNameCol, "bob")
                .validate().toString();
        System.out.println(insertCustomerQuery);

        // => INSERT INTO customer (cust_id,name)
        // VALUES (1,'bob')
        // 预编译
        String preparedInsertCustomerQuery = new InsertQuery(customerTable).addPreparedColumns(custIdCol, custNameCol).validate()
                .toString();
        System.out.println(preparedInsertCustomerQuery);

        // => INSERT INTO customer (cust_id,name)
        // VALUES (?,?)

        String insertOrderQuery = new InsertQuery(orderTable).addColumn(orderIdCol, 37).addColumn(orderCustIdCol, 1)
                .addColumn(orderTotalCol, 37.56).addColumn(orderDateCol, JdbcEscape.timestamp(new Date())).validate().toString();
        System.out.println(insertOrderQuery);

        // => INSERT INTO order (order_id,cust_id,total,order_date)
        // VALUES (37,1,37.56,{ts '2008-04-01 14:39:00.914'})

        //
        // find a customer name by id
        String query1 =
                new SelectQuery()
                        .addColumns(custNameCol)
                        .addCondition(BinaryCondition.equalTo(custIdCol, 1))
                        .validate().toString();
        System.out.println(query1);

        // => SELECT t0.name FROM customer t0
        //      WHERE (t0.cust_id = 1)

        ////
        // find all the orders for a customer, given name, order by date
        String query2 =
                new SelectQuery()
                        .addAllTableColumns(orderTable)
                        .addJoins(SelectQuery.JoinType.INNER, custOrderJoin)
                        .addCondition(BinaryCondition.equalTo(custNameCol, "bob"))
                        .addOrderings(orderDateCol)
                        .validate().toString();
        System.out.println(query2);

        // => SELECT t1.*
        //      FROM customer t0 INNER JOIN order t1 ON (t0.cust_id = t1.cust_id)
        //      WHERE (t0.name = 'bob')
        //      ORDER BY t1.order_date

        ////
        // find the totals of all orders for people named bob who spent over $100
        // this year, grouped by name
        String query3 =
                new SelectQuery()
                        .addCustomColumns(
                                custNameCol,
                                FunctionCall.sum().addColumnParams(orderTotalCol))
                        .addJoins(SelectQuery.JoinType.INNER, custOrderJoin)
                        .addCondition(BinaryCondition.like(custNameCol, "%bob%"))
                        .addCondition(BinaryCondition.greaterThan(
                                orderDateCol,
                                JdbcEscape.date(new Date(108, 0, 1)), true))
                        .addGroupings(custNameCol)
                        .addHaving(BinaryCondition.greaterThan(
                                FunctionCall.sum().addColumnParams(orderTotalCol),
                                100, false))
                        .validate().toString();
        System.out.println(query3);

        // => SELECT t0.name,SUM(t1.total)
        //      FROM customer t0 INNER JOIN order t1 ON (t0.cust_id = t1.cust_id)
        //      WHERE ((t0.name LIKE '%bob%') AND (t1.order_date >= {d '2008-01-01'}))
        //      GROUP BY t0.name
        //      HAVING (SUM(t1.total) > 100)

        ////
        // find addresses for customers from PA,NJ,DE from table:
        //   address(cust_id, street, city, state, zip)
        String customQuery1 =
                new SelectQuery()
                        .addCustomColumns(
                                custNameCol,
                                new CustomSql("a1.street"),
                                new CustomSql("a1.city"),
                                new CustomSql("a1.state"),
                                new CustomSql("a1.zip"))
                        .addCustomJoin(SelectQuery.JoinType.INNER, customerTable,
                                "address a1",
                                BinaryCondition.equalTo(custIdCol,
                                        new CustomSql("a1.cust_id")))
                        .addCondition(new InCondition("a1.state",
                                "PA", "NJ", "DE"))
                        .validate().toString();
        System.out.println(customQuery1);

        // => SELECT t0.name,a1.street,a1.city,a1.state,a1.zip
        //      FROM customer t0 INNER JOIN address a1 ON (t0.cust_id = a1.cust_id)
        //      WHERE ('a1.state' IN ('PA','NJ','DE') )
    }


}
