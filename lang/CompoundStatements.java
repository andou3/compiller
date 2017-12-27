import java.util.List;

class CompoundStatements {

	private List<Statement> statements = new ArrayList<>();

	void addStatement(Statement statement) {
		statement.add(statement);
	} 

	void invalidate() {
		statements.clear();
	}

	List<Statement> getStatements() {
		return statements;
	}

}