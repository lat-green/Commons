package com.greentree.common.util;


public enum TernarBoolean {

	FALSE {

		@Override
		public boolean bool() {
			return false;
		}
	},
	MAYBE {

		@Override
		public boolean bool() {
			throw new UnsupportedOperationException();
		}
	},
	TRUE {

		@Override
		public boolean bool() {
			return true;
		}
	},
	;

	public static TernarBoolean get(boolean bool) {
		if(bool)
			return TRUE;
		else
			return FALSE;
	}

	public abstract boolean bool();

}
