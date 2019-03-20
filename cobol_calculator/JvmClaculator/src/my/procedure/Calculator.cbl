      $set ilnamespace "my.procedure"
      $set ilsmartlinkage
       program-id. Calculator as "Calculator".

       data division.
       working-storage section.

       linkage section.
       01 lnk-arg1           pic 9(5) comp-3.
       01 lnk-arg2           pic 9(5) comp-3.
       01 lnk-sum            pic 9(5) comp-3.
       *> functions
       01 func               pic 9.
       copy "functions.cpy".
       *> Return-code values
       copy "returncode.cpy".
       
       procedure division using by value lnk-arg1, 
                                by value lnk-arg2,
                                by value func, 
                                by reference lnk-sum.                    
         *> Assume success
         move err-success to return-code

         evaluate func
               when addition           perform additionFunc
               when subtraction        perform subtractionFunc
               when multiplication     perform multiplicationFunc
               when #division          perform divisionFunc
               when other              perform errorFunc
         end-evaluate

		 goback.
         
      *****************************************************************
      * Functions
      *****************************************************************
       errorFunc section.
          *> Indicate failure
          move err-unknown-function to return-code.
		 
       additionFunc section.
          add lnk-arg1 to lnk-arg2 giving lnk-sum.

       subtractionFunc section.
          subtract lnk-arg1 from lnk-arg2 giving lnk-sum.
		   
	   multiplicationFunc section.
          multiply lnk-arg1 by lnk-arg2 giving lnk-sum.
		   
	   divisionFunc section.
          divide lnk-arg1 into lnk-arg2 giving lnk-sum.

           goback.

       end program Calculator.
