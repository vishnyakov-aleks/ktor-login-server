/*
 * This file is generated by jOOQ.
*/
package authserver.database.generated.tables.records;


import authserver.database.generated.tables.Updatedb;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


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
public class UpdatedbRecord extends TableRecordImpl<UpdatedbRecord> implements Record2<Integer, Long> {

    private static final long serialVersionUID = 1773630029;

    /**
     * Setter for <code>adv_ktor_login_server.updatedb.version</code>.
     */
    public void setVersion(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>adv_ktor_login_server.updatedb.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>adv_ktor_login_server.updatedb.update_time</code>.
     */
    public void setUpdateTime(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>adv_ktor_login_server.updatedb.update_time</code>.
     */
    public Long getUpdateTime() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Updatedb.UPDATEDB.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return Updatedb.UPDATEDB.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component2() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdatedbRecord value1(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdatedbRecord value2(Long value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdatedbRecord values(Integer value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UpdatedbRecord
     */
    public UpdatedbRecord() {
        super(Updatedb.UPDATEDB);
    }

    /**
     * Create a detached, initialised UpdatedbRecord
     */
    public UpdatedbRecord(Integer version, Long updateTime) {
        super(Updatedb.UPDATEDB);

        set(0, version);
        set(1, updateTime);
    }
}