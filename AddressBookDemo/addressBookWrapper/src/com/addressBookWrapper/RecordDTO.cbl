      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************

      *>> <summary>
      *>> A Data Transfer Object (DTO) for transferring data bewtween 
      *>> a procedural cobol and CRUD operations over tha backend
      *>> <summary>
       class-id com.addressBookWrapper.RecordDTO public.

       working-storage section.
       01 recordID binary-long property with no set public value 0.
       01 #name string property public value "".
       01 family string property public value "".
       01 postalCode binary-long property public value 0.
       01 city string property public value "".
       01 street string property public value "".
       01 phone string property public value "".

      *>> <summary>
      *>> Constructor
      *>> <summary>
       method-id New protected.
       procedure division using by value #id as binary-long.
           set recordID to #id
           goback.
       end method.

      *>> <summary>
      *>> toString
      *>> <summary>
       method-id toString override.
       procedure division returning return-value as string.
           set return-value to "id: " & recordID 
           & " name: " & #name & " " & family 
           & " address: " & street & " " & postalCode & " " & city
           & " phone: " & phone
           goback.
       end method.


       end class.
