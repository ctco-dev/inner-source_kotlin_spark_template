/*
 * This file is generated by jOOQ.
*/
package com.home.jooq;


import com.home.jooq.tables.Data;
import com.home.jooq.tables.Role;
import com.home.jooq.tables.User;
import com.home.jooq.tables.UserRole;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
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
public class Tables {

    /**
     * The table <code>public.data</code>.
     */
    public static final Data DATA = com.home.jooq.tables.Data.DATA;

    /**
     * The table <code>public.role</code>.
     */
    public static final Role ROLE = com.home.jooq.tables.Role.ROLE;

    /**
     * The table <code>public.user</code>.
     */
    public static final User USER = com.home.jooq.tables.User.USER;

    /**
     * The table <code>public.user_role</code>.
     */
    public static final UserRole USER_ROLE = com.home.jooq.tables.UserRole.USER_ROLE;
}