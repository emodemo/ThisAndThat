      *> Show usage of Calculator program 
       program-id. CallCalculator as "CallCalculator".

       environment division.
       configuration section.

       data division.
       working-storage section.
       01 #sum pic 9(5) comp-3.
       01 func pic 9.
       copy "functions.cpy".
       
       *> call native program
       procedure division.
           move addition to func
           call "Calculator" using by value 1
                                   by value 2
                                   by value func
                                   by reference #sum.
           display #sum.
           goback.

       end program CallCalculator.
