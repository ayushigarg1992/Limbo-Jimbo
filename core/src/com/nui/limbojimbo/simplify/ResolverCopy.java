package com.nui.limbojimbo.simplify;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nui.limbojimbo.SwipeResolver;

public class ResolverCopy implements SwipeResolver {
	
	@Override
	public void resolve(Array<Vector2> input, Array<Vector2> output) {
		output.clear(); 
		output.addAll(input);
	}

}
