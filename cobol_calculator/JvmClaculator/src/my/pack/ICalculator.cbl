       interface-id my.pack.ICalculator public.

        *>> ADD
       method-id add.
       procedure division using by value firstArg   as binary-long
                                by value secondArg  as binary-long
                               returning result     as binary-long.
       end method.

      *>> SUBTRACT
       method-id subtract.
       procedure division using by value firstArg   as binary-long
                                by value secondArg  as binary-long
                               returning result     as binary-long.
       end method.

      *>> MULTIPLY
       method-id multiply (firstArg as binary-long, secondArg as binary-long) 
                          returning result as binary-long.
       end method.
       
      
      *>> DIVIDE
       method-id divide (firstArg as binary-long, secondArg as binary-long) 
                        returning result as binary-long.
       end method.
       
      *>> RANDOM 
       method-id randomNumber.
       procedure division returning result as binary-long.
       end method.
       
       
       end interface.
