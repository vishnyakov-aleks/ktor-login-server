/*
 * This file is generated by jOOQ.
*/
package authserver.database.generated.tables;


import authserver.database.generated.AdvKtorLoginServer;
import authserver.database.generated.Keys;
import authserver.database.generated.tables.records.RestorePasswordCodesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class RestorePasswordCodes extends TableImpl<RestorePasswordCodesRecord> {

    private static final long serialVersionUID = -604637362;

    /**
     * The reference instance of <code>adv_ktor_login_server.restore_password_codes</code>
     */
    public static final RestorePasswordCodes RESTORE_PASSWORD_CODES = new RestorePasswordCodes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RestorePasswordCodesRecord> getRecordType() {
        return RestorePasswordCodesRecord.class;
    }

    /**
     * The column <code>adv_ktor_login_server.restore_password_codes.user_id</code>.
     */
    public final TableField<RestorePasswordCodesRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>adv_ktor_login_server.restore_password_codes.expire_time</code>.
     */
    public final TableField<RestorePasswordCodesRecord, Long> EXPIRE_TIME = createField("expire_time", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>adv_ktor_login_server.restore_password_codes.code</code>.
     */
    public final TableField<RestorePasswordCodesRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * Create a <code>adv_ktor_login_server.restore_password_codes</code> table reference
     */
    public RestorePasswordCodes() {
        this(DSL.name("restore_password_codes"), null);
    }

    /**
     * Create an aliased <code>adv_ktor_login_server.restore_password_codes</code> table reference
     */
    public RestorePasswordCodes(String alias) {
        this(DSL.name(alias), RESTORE_PASSWORD_CODES);
    }

    /**
     * Create an aliased <code>adv_ktor_login_server.restore_password_codes</code> table reference
     */
    public RestorePasswordCodes(Name alias) {
        this(alias, RESTORE_PASSWORD_CODES);
    }

    private RestorePasswordCodes(Name alias, Table<RestorePasswordCodesRecord> aliased) {
        this(alias, aliased, null);
    }

    private RestorePasswordCodes(Name alias, Table<RestorePasswordCodesRecord> aliased, Field<?>[] parameters) {
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
    public List<ForeignKey<RestorePasswordCodesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<RestorePasswordCodesRecord, ?>>asList(Keys.RESTORE_PASSWORD_CODES__RESTORE_PASSWORD_CODES_USERS_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestorePasswordCodes as(String alias) {
        return new RestorePasswordCodes(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestorePasswordCodes as(Name alias) {
        return new RestorePasswordCodes(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RestorePasswordCodes rename(String name) {
        return new RestorePasswordCodes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RestorePasswordCodes rename(Name name) {
        return new RestorePasswordCodes(name, null);
    }
}
