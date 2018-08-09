package mvc.util;

public class Fmt{

    // Flags.
    /// Zero-fill.
    public static final int ZF = 1;
    /// Left justify.
    public static final int LJ = 2;
    /// Hexadecimal.
    public static final int HX = 4;
    /// Octal.
    public static final int OC = 8;
    // Was a number - internal use.
    private static final int WN = 16;

    public static String fmt( int i, int minWidth )	{
    	return fmt( i, minWidth, 0 );
	  }

    public static String fmt( int i, int minWidth, int flags )	{
      boolean hexadecimal = ( ( flags & HX ) != 0 );
      boolean octal = ( ( flags & OC ) != 0 );
      if ( hexadecimal )
          return fmt(Long.toString( i & 0xffffffffL, 16 ), minWidth, flags|WN );
      else if ( octal )
          return fmt(Long.toString( i & 0xffffffffL, 8 ), minWidth, flags|WN );
      else
          return fmt( Integer.toString( i ), minWidth, flags|WN );
	}

    public static String fmt( String s, int minWidth, int flags )	{
      int len = s.length();
      boolean zeroFill = ( ( flags & ZF ) != 0 );
      boolean leftJustify = ( ( flags & LJ ) != 0 );
      boolean hexadecimal = ( ( flags & HX ) != 0 );
      boolean octal = ( ( flags & OC ) != 0 );
      boolean wasNumber = ( ( flags & WN ) != 0 );
      if ( ( hexadecimal || octal || zeroFill ) && ! wasNumber )
          throw new InternalError( "Fmt: number flag on a non-number" );
      if ( zeroFill && leftJustify )
          throw new InternalError( "Fmt: zero-fill left-justify is silly" );
      if ( hexadecimal && octal )
          throw new InternalError( "Fmt: can't do both hex and octal" );
      if ( len >= minWidth )
          return s;
      int fillWidth = minWidth - len;
      StringBuffer fill = new StringBuffer( fillWidth );
      for ( int i = 0; i < fillWidth; ++i )
          if ( zeroFill )
            fill.append( '0' );
          else
            fill.append( ' ' );
      if ( leftJustify )
          return s + fill;
      else if ( zeroFill && s.startsWith( "-" ) )
          return "-" + fill + s.substring( 1 );
      else
          return fill + s;
	}
}
