      *> package
      $set ilnamespace "my.pack"
      *> imports
      $set ilusing(java.util)
      $set ilusing(my.procedure)

       class-id MyCalculator public implements type ICalculator.

       working-storage section.
       *> pointer to the calculator program
       01 pp procedure-pointer.
       78 PROCEDURAL_PROGRAM value "my.procedure.Calculator".

      *>> CONSTRUCTOR
       method-id New protected.
       procedure division.
           set pp to entry PROCEDURAL_PROGRAM
           goback.
       end method.

      *>> ADD
       method-id add.
       local-storage section.
       01 func pic 99.
       copy "functions.cpy".
       procedure division using by value firstArg   as binary-long
                                by value secondArg  as binary-long
                               returning result     as binary-long.

           move addition to func
           set result to self::callCalculator(firstArg, secondArg, func)
           goback.
       end method.

      *>> SUBTRACT
       method-id subtract.
       local-storage section.
       01 func pic 99.
       copy "functions.cpy".
       procedure division using by value firstArg   as binary-long
                                by value secondArg  as binary-long
                               returning result     as binary-long.
           
           move subtraction to func
           set result to self::callCalculator(firstArg, secondArg, func)
           goback.
       end method.

      *>> MULTIPLY
       method-id multiply (firstArg as binary-long, secondArg as binary-long) 
                          returning result as binary-long.
           
           copy "functions.cpy".
           set result to self::callCalculator(firstArg, secondArg, multiplication)
           goback.
       end method.
       
      
      *>> DIVIDE
       method-id divide (firstArg as binary-long, secondArg as binary-long) 
                        returning result as binary-long.
           
           copy "functions.cpy".
           set result to self::callCalculator(firstArg, secondArg, #division)
           goback.
       end method.
       
      *>> RANDOM 
       method-id randomNumber.
       procedure division returning result as binary-long.
           declare rand = new Random()
           set result = rand::nextInt()
           goback.
       end method.
       

      *> CALL CALCULATOR
       method-id callCalculator private.
       local-storage section.  
       *> type used by the calculator program
       01 arg1     PIC 9(5) COMP-3.
       01 arg2     PIC 9(5) COMP-3.
       01 argsum   PIC 9(5) COMP-3.
       *> input params
       linkage section
       01 firstArg  binary-long.
       01 secondArg binary-long.
       01 result    binary-long.
       01 #function pic 9
       copy "functions.cpy".
       
       procedure division using by value firstArg 
                                by value secondArg 
                                by value #function
                          returning result.
                          
           move firstArg  to arg1
           move secondArg to arg2
           call pp using by value arg1 by value arg2 by value #function by reference argsum
           move argsum to result
           goback.
           
       end method. 

       end class.
