/*
 * This file is generated by jOOQ.
*/
package com.home.jooq;


import com.home.jooq.tables.Data;
import com.home.jooq.tables.Role;
import com.home.jooq.tables.User;
import com.home.jooq.tables.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1",
        "schema version:3"
    },
    date = "2018-07-20T18:48:07.188Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -363628787;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.data</code>.
     */
    public final Data DATA = com.home.jooq.tables.Data.DATA;

    /**
     * The table <code>public.role</code>.
     */
    public final Role ROLE = com.home.jooq.tables.Role.ROLE;

    /**
     * The table <code>public.user</code>.
     */
    public final User USER = com.home.jooq.tables.User.USER;

    /**
     * The table <code>public.user_role</code>.
     */
    public final UserRole USER_ROLE = com.home.jooq.tables.UserRole.USER_ROLE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.SEQ_DATA_ID);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Data.DATA,
            Role.ROLE,
            User.USER,
            UserRole.USER_ROLE);
    }
}
