package com.greentree.common.graphics.sgl;

public abstract class GLObject {
	
	protected final int glID;
	private final GLFWContext context;
	
	protected GLObject(final int glID) {
		this.glID = glID;
		this.context = GLFWContext.currentContext();
	}
	
	@Override
	protected void finalize() {
		context.putOnDelete(this);
	}
	
	protected abstract void delete();
	
	public static int getID(final GLObject obj) {
		if(obj != null)
			return obj.glID;
		return 0;
	}
	
	public int __glID() {
		return glID;
	}
	
	public final void bind() {
		binder().bind(glID);
	}
	
	public abstract GLObjectBinder binder();
	
	public final void unbind() {
		binder().unbind();
	}
	
	protected int glID() {
		return glID;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + glID + "]";
	}
	
	public static abstract class GLObjectBinder {
		
		public static final GLObjectBinder NULL = new GLObjectBinder() {
			
			@Override
			protected void glBind(int glID) {
			}
			
		};
		
		private int bind = 0;
		
		public final void bind(int glID) {
			if(bind == glID)
				return;
			bind = glID;
			glBind(glID);
		}
		
		public int getBind() {
			return bind;
		}
		
		public final void unbind() {
			bind(0);
		}
		
		protected abstract void glBind(int glID);
		
	}
	
}
