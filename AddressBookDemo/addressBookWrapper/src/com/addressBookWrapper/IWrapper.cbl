      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************
       
      * common interface for dis/connecting and performing CRUD operations
       interface-id com.addressBookWrapper.IWrapper public.

      *>> <summary>
      *>> Ensures a data source for reading.writing is available
      *>> <summary>
       method-id openConnection.
       procedure division.
       end method.

      *>> <summary>
      *>> Ensures a data source for reading.writing is closed
      *>> <summary>
       method-id closeConnection.
       procedure division.
       end method.

      *>> <summary>
      *>> Create and persist new record
      *>> <summary>
       method-id createRecord.
       procedure division returning return-value as type RecordDTO.
       end method.
      
      *>> <summary>
      *>> Update a record
      *>> <summary>
       method-id updateRecord.
       procedure division using by value dto as type RecordDTO.
       end method.

      *>> <summary>
      *>> Delete a record
      *>> <summary>
       method-id deleteRecord.
       procedure division using by value dto as type RecordDTO.
       end method.

      *>> <summary>
      *>> Get all available record
      *>> <summary>
       method-id getAllRecords.
       procedure division returning return-value as list[type RecordDTO].
       end method.

      *>> <summary>
      *>> Get the max number of possible records
      *>> <summary>
       method-id maxNumberOfRecords.
       procedure division returning return-value as binary-long.
       end method.

       end interface.
