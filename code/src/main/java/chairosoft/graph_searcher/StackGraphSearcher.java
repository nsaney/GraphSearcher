package chairosoft.graph_searcher;

import java.util.Stack;

public abstract class StackGraphSearcher<N> extends GraphSearcher<N, Stack<N>> {
    
    protected StackGraphSearcher() {
        super(new Stack<>());
    }
    
    @Override
    public void doAddNode(N node) throws Exception {
        this.workingCollection.push(node);
    }
    
    @Override
    public N doRemoveNode() throws Exception {
        return this.workingCollection.pop();
    }
}
