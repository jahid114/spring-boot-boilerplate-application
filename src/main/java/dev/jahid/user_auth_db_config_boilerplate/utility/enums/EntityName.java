package dev.jahid.user_auth_db_config_boilerplate.utility.enums;

import lombok.Getter;

@Getter
public enum EntityName {
    USER( "User" ),
    PROJECT( "Project" ),
    BANK( "Bank" ),
    SUPPLIER( "Supplier" ),
    MATERIAL( "Material" ),
    SUPPLY_RECORD( "Supply Record" ),
    SITE_MANAGER_EXPENSE( "Site Manager Expense" ),
    HEAD_OFFICE_EXPENSE( "Head Office Expense" ),
    HEAD_OFFICE_INCOME( "Head Office Income" ),
    CONTRACTOR_LEDGER( "Contractor Ledger" ),
    MATERIAL_USAGE_LOG( "Material Usage Log" ),
    WORK( "Work" );

    private final String name;

    EntityName( String name ) {
        this.name = name;
    }
}
