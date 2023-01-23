package org.wust.carshop;

import org.jdbi.v3.core.Jdbi;
import org.postgresql.core.Utils;
import org.postgresql.ds.PGSimpleDataSource;
import org.wust.carshop.model.Address;
import org.wust.carshop.model.Employee;
import org.wust.carshop.model.Part;
import org.wust.carshop.service.*;
import org.wust.carshop.util.PartPair;
import org.wust.carshop.view.LoginUI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        new LoginUI();
    }
}