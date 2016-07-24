package org.bytecodeandcode.java.loopobjects.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.bytecodeandcode.java.loopobjects.domain.FidInstance;
import org.bytecodeandcode.java.loopobjects.domain.FidInstanceMap;
import org.gs1ca.tradeitem.IllegalAccessException;
import org.gs1ca.tradeitem.TradeItem;
import org.gs1ca.tradeitem.attributevalue.ArrayAttributeValue;
import org.gs1ca.tradeitem.attributevalue.BaseAttributeValue;

import com.google.common.collect.Maps;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FidInstanceMapUtil {
	
	
	public FidInstanceMap convertToFidInstanceMap(TradeItem tradeItem, String lowestDimensionFid) {
		FidInstanceMap map = new FidInstanceMap();
		
		LoopObject parentLoopObject = getLoopObject(tradeItem, lowestDimensionFid);
		Iterator<String> attributeIds = tradeItem.attributeIds();
		while (attributeIds.hasNext()) {
			String fid = (String) attributeIds.next();
			BaseAttributeValue bav = tradeItem.getAttributeValue(fid);
			mapToLoopObject(fid, bav, parentLoopObject, map);
			try {
				parentLoopObject.loop();
			} catch (IllegalAccessException e) {
				continue;
			}			
		}
		
		return map;
	}

	private void mapToLoopObject(String fid, BaseAttributeValue bav, LoopObject loopObject, FidInstanceMap map) {
		
		IndexMap indexMap = buildIndexMap(bav);
		
		loopObject
			.value(bav)
			.map(map)
			.fid(fid)
			.instanceLimit(indexMap.getNumberOfInstances());
		
		if(loopObject.child() != null) {
			mapToLoopObject(fid, bav, loopObject.child(), map);
		}
	}

	private IndexMap buildIndexMap(BaseAttributeValue bav) {
		IndexMap idx;
		if (bav instanceof ArrayAttributeValue) {
			ArrayAttributeValue arrayAttributeValue = (ArrayAttributeValue) bav;
			int size = arrayAttributeValue.size();
			idx = new IndexMap(size);
			
			if (arrayAttributeValue.getDimension() == 1)
				return idx;
			
			try {
				for (int i = 0; i < arrayAttributeValue.size(); i++) {
					BaseAttributeValue memberValue = arrayAttributeValue.getArrayValue(i);
					IndexMap childIndex = buildIndexMap(memberValue);
					idx.children().put(i, childIndex);
					
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		} else {
			return null;
		}
		
		return idx;
	}

	/**
	 * 
	 * @param tradeItem
	 * @param lowestDimensionFid
	 * @return
	 */
	public LoopObject getLoopObject(TradeItem tradeItem, String lowestDimensionFid) {

		LoopObject currentLoopObject = null;
		// FIXME: Needs to be a sum of the capacities, not just the maximum in one.
		BaseAttributeValue attributeValue = tradeItem.getAttributeValue(lowestDimensionFid);
		if (!(attributeValue instanceof ArrayAttributeValue)) {
			throw new IllegalArgumentException("fid[" + lowestDimensionFid + "] must be of ArrayAttributeValue type");
		}
		ArrayAttributeValue arrayAttributeValue = (ArrayAttributeValue) attributeValue;

		List<Integer> maxCapacities = getSumCapacities(arrayAttributeValue);

		for (Integer maxCapacity : maxCapacities) {
			LoopObject childLoopObject = LoopObject.builder().maxCapacity(maxCapacity).build();
			childLoopObject.parent(currentLoopObject);
			currentLoopObject = childLoopObject;
		}
		
		// Go back to the top
		while (currentLoopObject.parent() != null) {
			currentLoopObject = currentLoopObject.parent();
		}

		return currentLoopObject;

	}

	private List<Integer> getSumCapacities(ArrayAttributeValue arrayAttributeValue) {
		List<Integer> sumCapacities = new ArrayList<>(arrayAttributeValue.getDimension() + 1);
		
		sumCapacities.add(arrayAttributeValue.size());
		
		if (arrayAttributeValue.getDimension() == 1)
			return sumCapacities;
		
		// multiple dimensions
		try {
			for (BaseAttributeValue memberValue : arrayAttributeValue.getArrayValue()) {
				if (memberValue == null)
					continue;
				
				List<Integer> subSumCapacities = getSumCapacities(((ArrayAttributeValue)memberValue));
				for (int i = 1; i < arrayAttributeValue.getDimension(); i++) {
					Integer currentSum = 0;
					if (sumCapacities.size() > i) {
						currentSum = sumCapacities.get(i);
						sumCapacities.set(i, currentSum + subSumCapacities.get(i - 1)); 
						continue;
					}
					sumCapacities.add(subSumCapacities.get(i - 1));				
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sumCapacities;
	}
	
	
}

@Data
@Accessors(fluent=true)
@Builder
class LoopObject implements Looping{
	
	private final int maxCapacity;
	private int currentIndex;
	private LoopObject child;
	private LoopObject parent;
	
	private FidInstanceMap map;
	private BaseAttributeValue value;
	private String fid;
	private int instanceLimit;
	
	@Override
	public void loop() throws IllegalAccessException {
		if (child == null)
			// Just one for the lowest level
			instanceLimit = currentIndex + 1; 
		else
			instanceLimit = child.instanceLimit;
		
		while (currentIndex < instanceLimit) {
			if (value instanceof ArrayAttributeValue) {
				try {
					value = value.getArrayValue(currentIndex);
				} catch (Exception e) {
					continue;
				}
			}
			map.put(new FidInstance(fid, new int[] {currentIndex}, null), value);
			if (child != null) {
				child.value = value;
				child.loop();
			}
			currentIndex++;

		}
	}
	
	public void parent(LoopObject parentLoopObject) {
		if (parentLoopObject != null)
			parentLoopObject.child = this;
		this.parent = parentLoopObject;
	}
	
	public void child(LoopObject childLoopObject) {
		this.child = childLoopObject;
		
		if (childLoopObject != null)
			childLoopObject.parent = this;
	}

	/**
	 * Collect all current indices in the indices object from top to bottom.
	 */
	@Override
	public Stack<Integer> getIndices() {
		Stack<Integer> indices = new Stack<>();
		
		// Traverse upwards
		LoopObject currentLoopObject = this;
		while (currentLoopObject.parent() != null) {
			currentLoopObject = currentLoopObject.parent();
		}
		
		// Traverse downwards
		indices.add(currentLoopObject.currentIndex());
		while(currentLoopObject.child() != null) {
			currentLoopObject = currentLoopObject.child();
			indices.add(currentLoopObject.currentIndex());
		}
		
		return indices;
	}

	@Override
	public int[] getIndicesArray() {
		int[] indices = new int[getIndices().size()];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = getIndices().get(i);
		}
		return indices;
	}
}


interface Looping{
	void loop() throws IllegalAccessException;
	Stack<Integer> getIndices();
	int[] getIndicesArray();
}


@Data
@Accessors(fluent = true)
class IndexMap{
	private final int size;	
	private Map<Integer, IndexMap> children = Maps.newLinkedHashMap();
	
	public int getNumberOfInstances() {
		int num = 0;
		if (children.size() == 0)
			return size;
		
		for (Integer key : children.keySet()) {
			num += children.get(key).getNumberOfInstances();
		}
		
		return num;
	}
}