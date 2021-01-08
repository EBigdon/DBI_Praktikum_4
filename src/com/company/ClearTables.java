package com.company;
import static com.company.TableManager.executeQuerry;

/**
 * Drops Tables and then creates them again.
 */
@SuppressWarnings("SpellCheckingInspection")
public class ClearTables {
    /**
     * Clears the History table.
     */
    public static void clearHistory() {
        executeQuerry("DROP TABLE IF EXISTS history; ");
        createHistory();
    }
    /**
     * First drops the tables and then creates them again.
     */
    public static void clearTables() {
        dropTable();
        createBranches();
        createAccounts();
        createTellers();
        createHistory();
    }

    /**
     * drops the tables.
     */
    private static void dropTable() {
        executeQuerry("DROP TABLE IF EXISTS history; ");
        executeQuerry("DROP TABLE IF EXISTS accounts; ");
        executeQuerry("DROP TABLE IF EXISTS tellers; ");
        executeQuerry("DROP TABLE IF EXISTS branches; ");
    }

    /**
     * creates the Branches Table.
     */
    private static void createBranches() {
        String query = """
                create table branches
                ( branchid int not null,
                 branchname char(20) not null,
                 balance int not null,
                 address char(72) not null,
                 primary key (branchid) );""";
        executeQuerry(query);
    }

    /**
     * creates the Accounts Table.
     */
    private static void createAccounts() {
        String query = """
                create table accounts
                ( accid int not null,
                 name char(20) not null,
                 balance int not null,
                branchid int not null,
                address char(68) not null,
                primary key (accid),
                foreign key (branchid) references
                bench_database.branches(branchid)
                );""";
        executeQuerry(query);
    }

    /**
     * creates the Tellers Table.
     */
    private static void createTellers() {
        String query = """
                create table tellers
                ( tellerid int not null,
                 tellername char(20) not null,
                 balance int not null,
                 branchid int not null,
                 address char(68) not null,
                 primary key (tellerid),
                 foreign key (branchid) references branches(branchid)
                );\s""";
        executeQuerry(query);
    }

    /**
     * creates the History Table.
     */
    private static void createHistory() {
        String query = """
                create table history
                ( accid int not null,
                 tellerid int not null,
                 delta int not null,
                 branchid int not null,
                 accbalance int not null,
                 cmmnt char(30) not null,
                 foreign key (accid) references accounts(accid),
                 foreign key (tellerid) references tellers(tellerid),
                 foreign key (branchid) references branches(branchid) );\s""";
        executeQuerry(query);
    }
}
