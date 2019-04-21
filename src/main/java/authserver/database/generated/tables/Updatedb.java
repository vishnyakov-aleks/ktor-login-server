/*
 * This file is generated by jOOQ.
*/
package authserver.database.generated.tables;


import authserver.database.generated.AdvKtorLoginServer;
import authserver.database.generated.tables.records.UpdatedbRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Updatedb extends TableImpl<UpdatedbRecord> {

    private static final long serialVersionUID = 1435318202;

    /**
     * The reference instance of <code>adv_ktor_login_server.updatedb</code>
     */
    public static final Updatedb UPDATEDB = new Updatedb();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UpdatedbRecord> getRecordType() {
        return UpdatedbRecord.class;
    }

    /**
     * The column <code>adv_ktor_login_server.updatedb.version</code>.
     */
    public final TableField<UpdatedbRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>adv_ktor_login_server.updatedb.update_time</code>.
     */
    public final TableField<UpdatedbRecord, Long> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>adv_ktor_login_server.updatedb</code> table reference
     */
    public Updatedb() {
        this(DSL.name("updatedb"), null);
    }

    /**
     * Create an aliased <code>adv_ktor_login_server.updatedb</code> table reference
     */
    public Updatedb(String alias) {
        this(DSL.name(alias), UPDATEDB);
    }

    /**
     * Create an aliased <code>adv_ktor_login_server.updatedb</code> table reference
     */
    public Updatedb(Name alias) {
        this(alias, UPDATEDB);
    }

    private Updatedb(Name alias, Table<UpdatedbRecord> aliased) {
        this(alias, aliased, null);
    }

    private Updatedb(Name alias, Table<UpdatedbRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return AdvKtorLoginServer.ADV_KTOR_LOGIN_SERVER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Updatedb as(String alias) {
        return new Updatedb(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Updatedb as(Name alias) {
        return new Updatedb(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Updatedb rename(String name) {
        return new Updatedb(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Updatedb rename(Name name) {
        return new Updatedb(name, null);
    }
}
