package com.badlogic.audio.io;

import java.io.DataInputStream;
import java.io.InputStream;

public class EndianDataInputStream extends DataInputStream
{
	public EndianDataInputStream(InputStream in)
	{
		super(in);
	}

	public String read4ByteString( ) throws Exception
	{
		byte[] bytes = new byte[4];
		this.readFully(bytes);
		return new String( bytes, "US-ASCII" );
	}

	public short readShortLittleEndian( ) throws Exception
	{
		int result = this.readUnsignedByte();
		result |= this.readUnsignedByte() << 8;
		return (short)result;
	}

	public int readIntLittleEndian( ) throws Exception
	{
		int result = this.readUnsignedByte();
		result |= this.readUnsignedByte() << 8;
		result |= this.readUnsignedByte() << 16;
		result |= this.readUnsignedByte() << 24;
		return result;
	}

	public int readInt24BitLittleEndian( ) throws Exception
	{
		int result = this.readUnsignedByte();
		result |= this.readUnsignedByte() << 8;
		result |= this.readUnsignedByte() << 16;
		if( (result & ( 1 << 23 )) == 8388608 )
			result |= 0xff000000;
		return result;
	}

	public int readInt24Bit( ) throws Exception
	{
		int result = this.readUnsignedByte() << 16;
		result |= this.readUnsignedByte() << 8;
		result |= this.readUnsignedByte();
		return result;
	}
}
