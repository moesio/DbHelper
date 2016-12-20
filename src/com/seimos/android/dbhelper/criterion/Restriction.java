package com.seimos.android.dbhelper.criterion;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:17:02 PM
 */
public enum Restriction {

	GE {

		@Override
		public String getExpression() {
			return " >= ?";
		}

	},
	EQ {

		@Override
		public String getExpression() {
			return " = ?";
		}

	},
	NE {

		@Override
		public String getExpression() {
			return " <> ?";
		}

	},
	LIKE {

		@Override
		public String getExpression() {
			return " like ?";
		}

	},
	GT {

		@Override
		public String getExpression() {
			return " > ?";
		}

	},
	LT {

		@Override
		public String getExpression() {
			return " < ?";
		}

	},
	LE {

		@Override
		public String getExpression() {
			return " <= ?";
		}

	},
	ILIKE {

		@Override
		public String getExpression() {
			return " like ?";
		}

	},
	BETWEEN {

		@Override
		public String getExpression() {
			return " between ? and ?";
		}

	},
	IN {

		@Override
		public String getExpression() {
			return " in (?)";
		}

	},
	ISNULL {

		@Override
		public String getExpression() {
			return " is null";
		}

	},
	EQPROPERTY {

		@Override
		public String getExpression() {
			return " = ?";
		}

	},
	NEPROPERTY {

		@Override
		public String getExpression() {
			return " <> ? ";
		}

	},
	LTPROPERTY {

		@Override
		public String getExpression() {
			return " < ?";
		}

	},
	LEPROPERTY {

		@Override
		public String getExpression() {
			return " <= ?";
		}

	},
	GTPROPERTY {

		@Override
		public String getExpression() {
			return " > ?";
		}

	},
	GEPROPERTY {

		@Override
		public String getExpression() {
			return " >= ?";
		}

	},
	ISNOTNULL {

		@Override
		public String getExpression() {
			return " is not null";
		}

	};

	public abstract String getExpression();
}

// LogicalExpression and(Criterion lhs, Criterion rhs) {
// Conjunction and(Criterion... predicates) {
// LogicalExpression or(Criterion lhs, Criterion rhs) {
// Disjunction or(Criterion... predicates) {
// Criterion not(Criterion expression) {
// Criterion sqlRestriction(String sql, Object[] values, Type[] types) {
// Criterion sqlRestriction(String sql, Object value, Type type) {
// Criterion sqlRestriction(String sql) {
// Conjunction conjunction() {
// Disjunction disjunction() {
// Criterion allEq(Map propertyNameValues) {
// Criterion isEmpty(String propertyName) {
// Criterion isNotEmpty(String propertyName) {
// Criterion sizeEq(String propertyName, int size) {
// Criterion sizeNe(String propertyName, int size) {
// Criterion sizeGt(String propertyName, int size) {
// Criterion sizeLt(String propertyName, int size) {
// Criterion sizeGe(String propertyName, int size) {
// Criterion sizeLe(String propertyName, int size) {
// NaturalIdentifier naturalId() {
