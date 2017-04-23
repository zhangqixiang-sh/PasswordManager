package com.zqx.password_manage.util;

/**
 * Created by ZhangQixiang on 2017/2/14.
 */
public class SqlEncoder {
    private StringBuilder mSqlBuilder;

    private SqlEncoder() {
        mSqlBuilder = new StringBuilder();
    }

    public static SqlEncoder begin() {
        return new SqlEncoder();
    }

    public SqlEncoder deleteFrom(String tableName) {
        mSqlBuilder.append("delete from ").append(tableName);
        return this;
    }

    public SqlEncoder select(String... columns) {
         mSqlBuilder.append(" select ");
        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                mSqlBuilder.append(columns[i]).append(" ");
            } else {
                mSqlBuilder.append(columns[i]).append(", ");
            }
        }
        return this;
    }

    public SqlEncoder from(String tableName) {
        mSqlBuilder.append(" from ").append(tableName);
        return this;
    }

    public SqlEncoder where(String column) {
        mSqlBuilder.append(" where ").append(column);
        return this;
    }

    public SqlEncoder equalsTo() {
        mSqlBuilder.append(" = ?");
        return this;
    }

    public SqlEncoder like() {
        mSqlBuilder.append(" like ?");
        return this;
    }

    public SqlEncoder createTable(String tableName) {
        mSqlBuilder.append("create table ").append(tableName);
        return this;
    }

    public SqlEncoder columns(String... columns) {
        mSqlBuilder = mSqlBuilder.append(" ( ");
        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                mSqlBuilder.append(") ");
            } else {
                mSqlBuilder.append(", ");
            }
        }

        return this;
    }

    @Override
    public String toString() {
        return mSqlBuilder.toString();
    }

    public SqlEncoder insertInto(String tableName) {
        mSqlBuilder.append("insert into ").append(tableName);
        return this;
    }



}
