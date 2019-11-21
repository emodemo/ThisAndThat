      ******************************************************************
      *
      * Copyright (C) Micro Focus 1984-2018. All rights reserved.
      *
      * This sample code is supplied for demonstration purposes only
      * on an "as is" basis and is for use at your own risk.
      *
      ******************************************************************
      *>> <summary>
      *>> An IFacade provider
      *>> <summary>
       class-id com.addressBookWrapper.WrapperProvider public.

      *>> <summary>
      *>> Get the appropriate IWrapper implementation
      *>> <summary>
       method-id getWrapper static.
       procedure division returning return-value as type IWrapper.
           set return-value to new AddressBookWrapper()
           goback.
       end method.

       end class.
