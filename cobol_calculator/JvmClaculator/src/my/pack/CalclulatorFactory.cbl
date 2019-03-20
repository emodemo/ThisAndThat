       class-id my.pack.CalclulatorFactory public.

       working-storage section.

       method-id createCalculator static.
       local-storage section.
       procedure division returning calc as type ICalculator.
       
           set calc to new MyCalculator()
           goback.
       end method.
       
       end class.
