package nl.haploid.event;

public class RowChangeEvent {

	private Long id;

	private String tableName;

	private Long rowId;

	public Long getId() {
		return id;
	}

	public Long getRowId() {
		return rowId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
