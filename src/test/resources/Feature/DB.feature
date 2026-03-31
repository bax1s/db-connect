Feature: DB TESTS IS HERE





#                                       -------------------------JDBC------------------------
  @ipo-2211
  Scenario: DB Select Query with test context variables
    And Add DB url-"PRE_TELESALES_DB_URL" username-"PRE_TELESALES_DB_USERNAME" password-"PRE_TELESALES_DB_PASSWORD"
    And JDBC--SELECT Query-"SELECT * FROM TELESALES.LEAD WHERE PIN = 'RUSTAM1'" Variables-""
    And JDBC--Save value from response Column Name-"ID",Variable Name-"leadId"
    Then Verify testContext value-"leadId" equals-"230299"
    And Add DB url-"PRE_PARTNERS_DB_URL" username-"PRE_PARTNERS_DB_USERNAME" password-"PRE_PARTNERS_DB_PASSWORD"
    And JDBC--SELECT Query-"SELECT * FROM LOAN.PROCESS WHERE PIN = 'RUSTAM1' ORDER BY CREATED_DATE DESC" Variables-""
    And Add testContext-"processId" and value-"NULL"
    And JDBC--Save value from response Column Name-"PROCESS_ID",Variable Name-"processId"
    Then Verify testContext value-"processId" equals-"a0ef8dba-0161-4aec-9eaa-fe2ccff88860"
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And Add testContext-"pin" and value-"P0SK001"
    And MongoDb--SELECT DB Name-"e2eloan" Collection name-"cash_loan_process" sort-"{createdAt:-1}" find-"{'processVariables.pin':'?'}" find variables-"pin"
    And MongoDB--Save value from response Json Path-"$[0]._id",Variable Name-"processId"
    Then Verify testContext value-"processId" equals-"dd6cc823-6448-499b-b73c-7b04b3fef532"


  @ipo-2212
  Scenario: DB Select Query simple
    And Add DB url-"PRE_TELESALES_DB_URL" username-"PRE_TELESALES_DB_USERNAME" password-"PRE_TELESALES_DB_PASSWORD"
    And Add testContext-"pin" and value-"P0SK001"
    And JDBC--SELECT Query-"SELECT * FROM TELESALES.LEAD WHERE PIN = ?" Variables-"pin"
    And JDBC--Save value from response Column Name-"PHONE",Variable Name-"fullName"
    Then Verify testContext value-"fullNam1" equals-"552994841"

  @ipo-2213
  Scenario: DB DELETE Query
    And Add DB url-"PRE_TELESALES_DB_URL" username-"PRE_TELESALES_DB_USERNAME" password-"PRE_TELESALES_DB_PASSWORD"
    And JDBC--DELETE Query-"DELETE FROM TELESALES.LEAD WHERE PIN = 'UMC1111' AND ROWNUM = 1" Variables-""


  @ipo-2215
  Scenario: COMPLEX usage
    And Add DB url-"PRE_PARTNERS_DB_URL" username-"PRE_PARTNERS_DB_USERNAME" password-"PRE_PARTNERS_DB_PASSWORD"
    And JDBC--SELECT Query-"SELECT * FROM LOAN.PROCESS WHERE PIN = 'RUSTAM1' ORDER BY CREATED_DATE DESC" Variables-""
    And JDBC--Save value from response Column Name-"PROCESS_ID",Variable Name-"processId"
    And JDBC--DELETE Query-"DELETE LOAN.PROCESS_PRODUCT where PROCESS_ID= ?" Variables-"processId"

  @ipo-2216
  Scenario: COMPLEX usage 2
    And Add DB url-"PRE_TELESALES_DB_URL" username-"PRE_TELESALES_DB_USERNAME" password-"PRE_TELESALES_DB_PASSWORD"
    And Add testContext-"externalLeadId" and value-"0a66e10f-e3bb-49f3-97ea-512a22671158"
    And Add testContext-"state" and value-"COMPLETED"
    And Add testContext-"map" and value-""
    And JDBC--SELECT Query-"SELECT * FROM TELESALES.LEAD WHERE EXTERNAL_ID=?  and STATE =?" Variables-"externalLeadId,state"
    And JDBC--Save value from response Column Name-"ID",Variable Name-"leadId"


#                                       -------------------------MONGO DB------------------------

  @ipo-2216
  Scenario: SELECT Query  Mongo DB simple
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And MongoDb--SELECT DB Name-"e2eloan" Collection name-"cash_loan_process" sort-"{createdAt:-1}" find-"{'processVariables.pin':'P0SK001'}" find variables-""
    And MongoDB--Save value from response Json Path-"$[0]._id",Variable Name-"processId"

  @ipo-2217
  Scenario: SELECT Query  Mongo DB
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And Add testContext-"pin" and value-"P0SK001"
    And Add testContext-"source" and value-"ACARD"
    And MongoDb--SELECT DB Name-"e2eloan" Collection name-"cash_loan_process" sort-"{createdAt:-1}" find-"{'processVariables.pin':'?','processVariables.source':'?'}" find variables-"pin,source"
    And MongoDB--Save value from response Json Path-"$[0]._id",Variable Name-"processId"

  @ipo-2218
  Scenario: UPDATE Query  Mongo DB
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And MongoDb--UPDATE DB Name-"e2eloan" Collection name-"cash_loan_process" filter-"{'processVariables.pin':'0000UUM'}" updateOperation-"{'$set': {'processVariables.pin':'0000UUG'}}"

  @ipo-2219
  Scenario: DELETE Query  Mongo DB simple
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And MongoDb--DELETE DB Name-"e2eloan" Collection name-"cash_loan_process" filter-"{'processVariables.processStatus':'imtina'}" filter variables-""

  @ipo-2220
  Scenario: INSERT Query  Mongo DB
    And Add DB url-"PRE_CASH_LOAN_DB_URL" username-"PRE_CASH_LOAN_DB_USERNAME" password-"PRE_CASH_LOAN_DB_PASSWORD"
    And Add testContext-"processId" and value-"randomDA"
    And MongoDb--INSERT DB Name-"e2eloan" Collection name-"cash_loan_process" insertData-"{_id: '?', _class: 'az.kapitalbank.ms.cash.loan.entity.LoanProcessEntity'}" insert variables-"processId"

  @ipo-2221
  Scenario: oracle cvm hele tam deyilllll
    And JDBC--DELETE Query-"delete from CUST_RESERVE where CAMPAIGN=445" Variables-""
    And JDBC--SELECT Query-"select pkg_reserve.customer_reserve_count(null,null,null,null,null,10, 445) FROM DUAL" Variables-""
    And JDBC--SELECT Query-"SELECT * FROM LEAD.LEAD WHERE CAMPAIGN = 445" Variables-""
    And JDBC--Save multiple value from response Column Name-"AMOUNT"


