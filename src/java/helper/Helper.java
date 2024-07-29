/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package helper;

import model.Account;

public class Helper {
    public static boolean haveUser(Account account) {
        return account == null ? false : true;
    }
    
    public static boolean isAdmin(Account account) {
        if (!haveUser(account)) return false;
        if (account.getRole().equals("ad")) return true;
        return false;
    }
    
    public static boolean isAdminOrStaff(Account account) {
        if (!haveUser(account)) return false;
        if (account.getRole().equals("ad") || account.getRole().equals("st")) return true;
        return false;
    }
}
