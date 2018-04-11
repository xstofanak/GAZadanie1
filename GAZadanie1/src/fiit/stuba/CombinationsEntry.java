package fiit.stuba;

import java.util.List;

public class CombinationsEntry{
	
	private List<Vertex> combinationList;
	private Boolean flag;
	
	public CombinationsEntry(List<Vertex> combinationList, Boolean flag){
		
		this.combinationList = combinationList;
		this.flag = flag;		
	
	}
	
	public List<Vertex> getCombinationList() {
		return combinationList;
	}

	public void setCombinationList(List<Vertex> combinationList) {
		this.combinationList = combinationList;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}