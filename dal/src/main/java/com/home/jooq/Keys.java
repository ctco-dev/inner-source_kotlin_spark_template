/*
 * This file is generated by jOOQ.
*/
package com.home.jooq;


import com.home.jooq.tables.Data;
import com.home.jooq.tables.Role;
import com.home.jooq.tables.User;
import com.home.jooq.tables.UserRole;
import com.home.jooq.tables.records.DataRecord;
import com.home.jooq.tables.records.RoleRecord;
import com.home.jooq.tables.records.UserRecord;
import com.home.jooq.tables.records.UserRoleRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>public</code> schema.
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
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<DataRecord, Integer> IDENTITY_DATA = Identities0.IDENTITY_DATA;
    public static final Identity<RoleRecord, Integer> IDENTITY_ROLE = Identities0.IDENTITY_ROLE;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UserRoleRecord, Integer> IDENTITY_USER_ROLE = Identities0.IDENTITY_USER_ROLE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DataRecord> DATA_PKEY = UniqueKeys0.DATA_PKEY;
    public static final UniqueKey<RoleRecord> ROLE_PKEY = UniqueKeys0.ROLE_PKEY;
    public static final UniqueKey<UserRecord> USER_PKEY = UniqueKeys0.USER_PKEY;
    public static final UniqueKey<UserRoleRecord> USER_ROLE_PKEY = UniqueKeys0.USER_ROLE_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<UserRoleRecord, UserRecord> USER_ROLE__USER_ROLE_USER_ID_FKEY = ForeignKeys0.USER_ROLE__USER_ROLE_USER_ID_FKEY;
    public static final ForeignKey<UserRoleRecord, RoleRecord> USER_ROLE__USER_ROLE_ROLE_ID_FKEY = ForeignKeys0.USER_ROLE__USER_ROLE_ROLE_ID_FKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<DataRecord, Integer> IDENTITY_DATA = createIdentity(Data.DATA, Data.DATA.ID);
        public static Identity<RoleRecord, Integer> IDENTITY_ROLE = createIdentity(Role.ROLE, Role.ROLE.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
        public static Identity<UserRoleRecord, Integer> IDENTITY_USER_ROLE = createIdentity(UserRole.USER_ROLE, UserRole.USER_ROLE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<DataRecord> DATA_PKEY = createUniqueKey(Data.DATA, "data_pkey", Data.DATA.ID);
        public static final UniqueKey<RoleRecord> ROLE_PKEY = createUniqueKey(Role.ROLE, "role_pkey", Role.ROLE.ID);
        public static final UniqueKey<UserRecord> USER_PKEY = createUniqueKey(User.USER, "user_pkey", User.USER.ID);
        public static final UniqueKey<UserRoleRecord> USER_ROLE_PKEY = createUniqueKey(UserRole.USER_ROLE, "user_role_pkey", UserRole.USER_ROLE.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<UserRoleRecord, UserRecord> USER_ROLE__USER_ROLE_USER_ID_FKEY = createForeignKey(com.home.jooq.Keys.USER_PKEY, UserRole.USER_ROLE, "user_role__user_role_user_id_fkey", UserRole.USER_ROLE.USER_ID);
        public static final ForeignKey<UserRoleRecord, RoleRecord> USER_ROLE__USER_ROLE_ROLE_ID_FKEY = createForeignKey(com.home.jooq.Keys.ROLE_PKEY, UserRole.USER_ROLE, "user_role__user_role_role_id_fkey", UserRole.USER_ROLE.ROLE_ID);
    }
}
