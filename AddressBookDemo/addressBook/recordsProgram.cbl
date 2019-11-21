      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************
       program-id. recordsProgram as "recordsProgram".

       environment division. 
       input-output section.
       file-control.
           select datafile assign to ".\records.txt"
           organization is indexed
           access is dynamic
           record key recordId of rec with no duplicates.

       data division. 
       file section.
       fd datafile.
       01 rec.
       copy "records.cpy".

       working-storage section.
       01 eof pic x(1) value "N".
       01 sub pic 9(9) comp-5 value 1.
       78 maxNOfRecords value 100.

       linkage section.
       *> a record reference
       01 tmprec.
       copy "records.cpy".
       *> an array of records reference
       01 arrayParent.
           03 array occurs maxNOfRecords times.
           copy "records.cpy".
       *> an integer reference
       01 nOfRecords redefines arrayParent pic 9(4) comp.
       *> functions
       01 func                     pic 99.
       copy "functions.cpy".
       *> Return-code values
       copy "returncode.cpy".

       procedure division using func tmprec arrayParent.
           *> Assume success
           move err-success to return-code

           evaluate func
               when openFile           perform openFileFunc
               when closeFile          perform closeFileFunc
               when writeRecord        perform writeRecordFunc
               when rewriteRecord      perform rewriteRecordFunc
               when removeRecord       perform removeRecordFunc
               when readAllRecords     perform readAllRecordsFunc
               when maxNumberOfRecords perform maxNumberOfRecordsFunc
               when other              perform errorFunc
           end-evaluate

           goback
           .

      *****************************************************************
      * Functions
      *****************************************************************
       errorFunc section.
           *> Indicate failure
           move err-unknown-function to return-code.

       openFileFunc section.
           open i-o datafile.

       closeFileFunc section.
           close datafile.

       writeRecordFunc section.
           write rec from tmprec
               invalid key move err-duplicate-record to return-code
           end-write.

       rewriteRecordFunc section.
           set recordId of rec to recordId of tmprec.
           read datafile 
               key is recordId of rec
               invalid key 
                   move err-missing-record to return-code
               not invalid key
                   rewrite rec from tmprec end-rewrite
           end-read.

       removeRecordFunc section.
           set recordId of rec to recordId of tmprec.
           read datafile 
               key is recordId of rec
               invalid key 
                   move err-missing-record to return-code
               not invalid key
                   delete datafile record end-delete
           end-read.

       readAllRecordsFunc section.
           *> reset the subscript and end-of-file
           move 1 to sub.
           move "N" to eof.
           move zeros to recordId of rec.
           start datafile key is >= recordId of rec
           invalid key *> do nothing
           not invalid key *> read the file
               perform until eof="Y"
                  read datafile next record
                   at end 
                       move "Y" to eof
                   not at end 
                       move rec to array(sub)
                       add 1 to sub
                  end-read
               end-perform.

       maxNumberOfRecordsFunc section.
           move maxNOfRecords to nOfRecords.

       end program recordsProgram.
