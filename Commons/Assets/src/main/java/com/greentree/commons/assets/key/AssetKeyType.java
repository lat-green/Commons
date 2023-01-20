package com.greentree.commons.assets.key;

import java.io.Serializable;

public interface AssetKeyType extends Serializable {

	AssetKeyType DEFAULT = new AssetKeyType() {
		private static final long serialVersionUID = 1L;

		@Override
		public String toString() {
			return "AssetKeyType.DEFAULT";
		}

	};

}
