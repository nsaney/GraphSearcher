package chairosoft.graph_searcher;

import java.util.Collection;

public abstract class GraphSearcher<N, C extends Collection<? extends N>>
    implements Runnable {
    
    // Fields
    public final C workingCollection;
    
    // Constructor
    protected GraphSearcher(C _workingCollection) {
        this.workingCollection = _workingCollection;
    }
    
    // Instance Methods - Abstract
    public abstract void doAddNode(N node) throws Exception;
    public abstract N doRemoveNode() throws Exception;
    public abstract N createRootNode() throws Exception;
    public abstract Collection<? extends N> getChildrenOf(N node) throws Exception;
    
    // Instance Methods - Event Adapters
    public void beforeAddNode(N node) throws Exception {}
    public void afterAddNode(N node) throws Exception {}
    public void beforeRemoveNode() throws Exception {}
    public void afterRemoveNode(N node) throws Exception {}
    public boolean canAddChildrenFor(N node) throws Exception { return true; }
    public void beforeIteration(int currentIteration) throws Exception {}
    public void afterIteration(int currentiteration) throws Exception {}
    
    // Instance Methods - Concrete
    public void addNode(N node) throws Exception {
        this.beforeAddNode(node);
        this.doAddNode(node);
        this.afterAddNode(node);
    }
    
    public N removeNode() throws Exception {
        this.beforeRemoveNode();
        N node = this.doRemoveNode();
        this.afterRemoveNode(node);
        return node;
    }
    
    @Override
    public final void run() {
        try {
            this.doSearch();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void doSearch() throws Exception {
        N root = this.createRootNode();
        this.addNode(root);
        
        int currentIteration = 0;
        while (this.workingCollection.size() > 0) {
            ++currentIteration;
            this.beforeIteration(currentIteration);
            this.doIteration(currentIteration);
            this.afterIteration(currentIteration);
        }
    }
    
    public void doIteration(int currentIteration) throws Exception {
        N node = this.removeNode();
        if (!this.canAddChildrenFor(node)) { return; }
        Collection<? extends N> children = this.getChildrenOf(node);
        for (N child : children) {
            this.addNode(child);
        }
    }
}
