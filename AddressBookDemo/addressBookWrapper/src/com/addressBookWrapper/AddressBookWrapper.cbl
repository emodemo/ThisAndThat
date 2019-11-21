      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************

      *> imports
      $set ilusing(java.util)

      *>> <summary>
      *>> An IFacade implementation to handle conection 
      *>> and CRUD operations over tha backend
      *>> <summary>
       class-id com.addressBookWrapper.AddressBookWrapper public
                implements type IWrapper.


       working-storage section.
       *> procedural cobol pointer
       01 pp procedure-pointer private.
       *> private values
       01 #index binary-long value 0 private.
       78 PROCEDURAL_PROGRAM value "recordsProgram" private.
       01 func                     pic 99 private.
       copy "functions.cpy".


      *>> <summary>
      *>> Constructor
      *>> <summary>
       method-id New protected.
       procedure division.
           initialize func
           set pp to entry PROCEDURAL_PROGRAM
           goback.
       end method.

      *>> <summary> 
      *>> Ensures a data source for reading.writing is available
      *>> <summary>
       method-id openConnection.
       procedure division.
           move openFile to func
           call pp using func omitted omitted
           set #index to self::maxNumberOfRecords()
           goback.
       end method.

      *>> <summary>
      *>> Ensures a data source for reading.writing is closed
      *>> <summary>
       method-id closeConnection.
       procedure division.
           move closeFile to func
           call pp using func omitted omitted
           goback.
       end method.

      *>> <summary>
      *>> Create and persist new record
      *>> <summary>
       method-id createRecord.
       local-storage secion.
       01 rec.
          copy "records.cpy".
       procedure division returning return-value as type RecordDTO.
           declare #id as binary-long = self::getUniqueID()
           declare dto = new RecordDTO(#id)
           set rec to self::DTOtoRecord(dto)
           move writeRecord to func
           call pp using func rec omitted
           set return-value to dto
           goback.
       end method.

      *>> <summary>
      *>> Update a record
      *>> <summary>
       method-id updateRecord.
       local-storage secion.
       01 rec.
          copy "records.cpy".
       procedure division using by value dto as type RecordDTO.
           move rewriteRecord to func
           set rec to self::DTOtoRecord(dto)
           call pp using func rec omitted
           goback.
       end method.

      *>> <summary>
      *>> Delete a record
      *>> <summary>
       method-id deleteRecord.
       local-storage secion.
       01 rec.
          copy "records.cpy".
       procedure division using by value dto as type RecordDTO.
           move removeRecord to func
           set rec to self::DTOtoRecord(dto)
           call pp using func rec omitted
           goback.
       end method.

      *>> <summary>
      *>> Get all available record
      *>> <summary>
       method-id getAllRecords.
       local-storage section.
       01 arrayParent.
           03 array occurs 0 to 1000 depending on #index.
           copy "records.cpy".
       01 rec.
           copy "records.cpy".
       procedure division returning return-value as list[type RecordDTO].
           initialize arrayParent
           move readAllRecords to func
           call pp using func omitted arrayParent
           create return-value
           declare i as binary-long.
           perform varying i from 1 by 1 until i >= #index
               set rec to array(i)
               if recordId of rec not= 0
                   declare dto = self::recordToDTO(rec)
                   invoke return-value::add(dto)
                   *> an alternative syntax for putting element into a map:
                   *> write return-value from dto
               end-if
           end-perform. 

           goback.
       end method.

      *>> <summary>
      *>> Get the max number of possible records
      *>> <summary>
       method-id maxNumberOfRecords.
       local-storage section.
       01 val pic 9(4) comp value 0.
       procedure division returning return-value as binary-long.
           move maxNumberOfRecords to func
           call pp using func omitted val
           move val to return-value
           goback.
       end method.

      * find a unique ID 
       method-id getUniqueID private.
       procedure division returning return-value as binary-long.
           *> use imported java API
           declare rand = new Random()
           *> call recursive method
           set return-value = getAvailableID(rand, self::getAllRecords())
           goback.
       end method. 

      *> recursive method example 
       method-id getAvailableID private.
       local-storage section.
       01 #id binary-long value 0.
       procedure division using by value rand as type Random
                                by value recList as list[type RecordDTO]
                          returning return-value as binary-long.

           set #id to rand::nextInt()
           *> if/esle 
           if not containsRecordId(#id, recList) and #id not= 0
               set return-value = #id
           else
               set return-value = self::getAvailableID(rand, recList)
           end-if

           goback.
       end method.

       *> method with loop over a list
       method-id containsRecordId private.
       procedure division using by value #id as binary-long
                                by value recList as list[type RecordDTO]
                          returning return-value as condition-value.

           perform varying rec as type RecordDTO through recList
               if rec::recordID equal #id
                   set return-value to true
                   *> break the loop
                   exit perform
               end-if
           end-perform

           goback.
       end method.

      * translate from a procedural "record" 
      * to an object oriented Data Transfer Object
       method-id recordToDTO private.
       linkage section.
       01 rec.
          copy "records.cpy".
       procedure division using by value rec
                          returning dto as type RecordDTO.

           set dto = new RecordDTO(recordId of rec)
           set dto::name to recordName of rec
           set dto::family to recordFamily of rec
           set dto::postalCode to recordCode of rec
           set dto::city to recordCity of rec
           set dto::street to recordStreet of rec
           set dto::phone to recordPhone of rec

           goback.
       end method.

      * translate from an object oriented Data Transfer Object 
      * to a procedural "record"
       method-id DTOtoRecord private.
       linkage section.
       01 rec.
          copy "records.cpy".
       procedure division using by value dto as type RecordDTO
                          returning rec.
           initialize rec
           set recordId of rec to dto::recordID
           set recordName of rec to dto::name
           set recordFamily of rec to dto::family
           set recordCode of rec to dto::postalCode
           set recordCity of rec to dto::city
           set recordStreet of rec to dto::street
           set recordPhone of rec to dto::phone

           goback.
       end method.
       
       end class.
