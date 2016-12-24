package chairosoft.graph_searcher;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class QueueGraphSearcher<N> extends GraphSearcher<N, Queue<N>> {
    
    protected QueueGraphSearcher() {
        super(new ArrayDeque<>());
    }
    
    @Override
    public void doAddNode(N node) throws Exception {
        this.workingCollection.add(node);
    }
    
    @Override
    public N doRemoveNode() throws Exception {
        return this.workingCollection.remove();
    }
}
