package com.company;

public class TXManager {
    /**
     * Gets balance from Database with corresponding account id.
     *
     * @param accid id of account
     * @return balance of account
     */
    public static float balanceTx(final int accid) {
        return (float) 1;
    }

    /**
     * updates Balance of Tellers, Accounts and History with corresponding IDs.
     *
     * @param accid         account id
     * @param tellerid      tellers id
     * @param branchid      branch id
     * @param depositAmount amount which updates the balances.
     * @return updated balance
     */
    public static float depositTx(final int accid, final int tellerid, final int branchid, final float depositAmount) {
        return (float) 1;
    }

    /**
     * returns value of transactions with depositAmount value.
     * @param depositAmount DELTA
     * @return Number of previously logged deposits with exactly this amount
     */
    public static int analyseTx(final float depositAmount) {

    }
}
