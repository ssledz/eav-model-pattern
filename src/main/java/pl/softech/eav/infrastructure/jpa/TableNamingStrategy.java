package pl.softech.eav.infrastructure.jpa;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * @author ssledz 
 */
public class TableNamingStrategy extends DefaultNamingStrategy {

	private static final long serialVersionUID = 1L;

	private final static String TABLE_PREFIX = "eav";

	private final static String SEPARATOR = "_";

	@Override
	public String tableName(String tableName) {

		StringBuilder sb = new StringBuilder();
		sb.append(TABLE_PREFIX);
		sb.append(SEPARATOR);
		sb.append(tableName);

		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(TableNamingStrategy.class.getName());
	}

}
