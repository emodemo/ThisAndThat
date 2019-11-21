      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************

       *> Test Fixture for addressBook, recordsProgram

       copy "mfunit_prototypes.cpy".

       program-id. TestRecordsProgram.

       working-storage section.
       copy "mfunit.cpy".
       78 TEST-TESTRECORDSPROGRAM value "TestRecordsProgram".
       01 pp procedure-pointer.
       01 i pic 9(9) comp-5 value 1.

      *> Program linkage data
       01 rec1.
           copy "records.cpy".
       01 rec2.
           copy "records.cpy".
       01 arrayParent.
         03 array occurs 100.
            copy "records.cpy".
       01 nOfRecords pic 9(4) comp value 0.
       01 func                     pic 99.
       copy "functions.cpy".

       local-storage section.
       01 tmprec.
           copy "records.cpy".
       01 empty-name pic x(30) value " ".

       procedure division.
           goback returning 0
       .

       *> Configuraitions are in the SETUP/TEARDOWN entry points below
       *> STEPS:
       *> step 1 - create a record
       *> step 2 - update the 1st record
       *> step 3 - create another record
       *> step 4 - update the 2nd record
       *> step 5 - delete the 1st rec
       *> step 6 - delete the 2nd rec
       *> VERIFICATIONS:
       *> verify the result from each step
       entry MFU-TC-PREFIX & TEST-TESTRECORDSPROGRAM.

          *> step 1 - create a record
           move 1 to recordId of rec1.
           move writeRecord to func
           call pp using func rec1 omitted

           *> check step 1
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(1) to tmprec
           if recordId of tmprec not= recordId of rec1
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           *> step 2 - update the 1st record
           move "John" to recordName of rec1.
           move "Smith" to recordFamily of rec1.
           move 1000 to recordCode of rec1.
           move "Sofia" to recordCity of rec1.
           move "blvd. Tsar Asen" to recordStreet of rec1.
           move "00359123" to recordPhone of rec1
           move rewriteRecord to func
           call pp using func rec1 omitted

           *> check step 2
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(1) to tmprec
           if recordId of tmprec not= recordId of rec1 and recordName of tmprec not= recordName of rec1
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           *> step 3 - create another record   
           move 2 to recordId of rec2.
           move "Salvador" to recordName of rec2.
           move "Mirono" to recordFamily of rec2.
           move 1001 to recordCode of rec2.
           move "Roma" to recordCity of rec2.
           move "blvd. Cesario" to recordStreet of rec2.
           move "00359123" to recordPhone of rec2
           move writeRecord to func
           call pp using func rec2 omitted

           *> check step 3
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(2) to tmprec
           if recordId of tmprec not= recordId of rec2 and recordName of tmprec not= recordName of rec2
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           *> step 4 - update the 2nd record
           move "Haralampii" to recordName of rec2.
           move rewriteRecord to func
           call pp using func rec2 omitted

           *> check step 4
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(2) to tmprec
           if recordId of tmprec not= recordId of rec2 and recordName of tmprec not= recordName  of rec2
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           *> step 5 - delete the 1st rec
           move removeRecord to func
           call pp using func rec1 omitted

           *> check step 5 - only the 2nd record is present
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(1) to tmprec
           if recordId of tmprec not= recordId of rec2 and recordName of tmprec not= recordName of rec2
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           *> step 6 - delete the 2nd rec
           move removeRecord to func
           call pp using func rec2 omitted

           *> check step 6 - no records
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move array(1) to tmprec
           if recordId of tmprec not= 0 and recordName of tmprec not= empty-name
               call MFU-ASSERT-FAIL-Z using z"wrong record"
           end-if.

           goback returning MFU-PASS-RETURN-CODE
       .

      $region TestCase Configuration
       
       *> SETUP:
       *> initialize variables
       *> open the file
       entry MFU-TC-SETUP-PREFIX & TEST-TESTRECORDSPROGRAM.
           *> Load the library that is being tested
           set pp to entry "recordsProgram"

           initialize rec1
           initialize rec2
           initialize arrayParent
           initialize nOfRecords
           initialize func
           *> Add any other test setup code here

           *> open the file
           move openFile to func.
           call pp using func omitted omitted
           
           goback returning 0.

       *> TEARDOWN:
       *> delete any record that may be left
       *> close the file
       entry MFU-TC-TEARDOWN-PREFIX & TEST-TestRecordsProgram.
           *> delete the records
           move readAllRecords to func
           initialize arrayParent
           call pp using func omitted arrayParent
           move maxNumberOfRecords to func
           call pp using func omitted nOfRecords
           move removeRecord to func
           perform varying i from 1 by 1 until i >= nOfRecords
               call pp using func array(i) omitted
           end-perform. 
           *> close the file
           move closeFile to func
           call pp using func omitted omitted 
           
           goback returning 0.

      $end-region

       end program.
