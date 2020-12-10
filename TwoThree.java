//Samantha Michelle Garcia

class TwoThree {
	
	static void insert(String key, int value, TwoThreeTree tree) {
   // insert a key value pair into tree (overwrite existsing value
   // if key is already present)

      int h = tree.height;

      if (h == -1) {
          LeafNode newLeaf = new LeafNode();
          newLeaf.guide = key;
          newLeaf.value = value;
          tree.root = newLeaf; 
          tree.height = 0;
      }
      else {
         WorkSpace ws = doInsert(key, value, tree.root, h);

         if (ws != null && ws.newNode != null) {
         // create a new root

            InternalNode newRoot = new InternalNode();
            if (ws.offset == 0) {
               newRoot.child0 = ws.newNode; 
               newRoot.child1 = tree.root;
            }
            else {
               newRoot.child0 = tree.root; 
               newRoot.child1 = ws.newNode;
            }
            resetGuide(newRoot);
            tree.root = newRoot;
            tree.height = h+1;
         }
      }
   }

   static WorkSpace doInsert(String key, int value, Node p, int h) {
   // auxiliary recursive routine for insert

      if (h == 0) {
         // we're at the leaf level, so compare and 
         // either update value or insert new leaf

         LeafNode leaf = (LeafNode) p; //downcast
         int cmp = key.compareTo(leaf.guide);

         if (cmp == 0) {
            leaf.value = value; 
            return null;
         }

         // create new leaf node and insert into tree
         LeafNode newLeaf = new LeafNode();
         newLeaf.guide = key; 
         newLeaf.value = value;

         int offset = (cmp < 0) ? 0 : 1;
         // offset == 0 => newLeaf inserted as left sibling
         // offset == 1 => newLeaf inserted as right sibling

         WorkSpace ws = new WorkSpace();
         ws.newNode = newLeaf;
         ws.offset = offset;
         ws.scratch = new Node[4];

         return ws;
      }
      else {
         InternalNode q = (InternalNode) p; // downcast
         int pos;
         WorkSpace ws;

         if (key.compareTo(q.child0.guide) <= 0) {
            pos = 0; 
            ws = doInsert(key, value, q.child0, h-1);
         }
         else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
            pos = 1;
            ws = doInsert(key, value, q.child1, h-1);
         }
         else {
            pos = 2; 
            ws = doInsert(key, value, q.child2, h-1);
         }

         if (ws != null) {
            if (ws.newNode != null) {
               // make ws.newNode child # pos + ws.offset of q

               int sz = copyOutChildren(q, ws.scratch);
               insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);
               if (sz == 2) {
                  ws.newNode = null;
                  ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
               }
               else {
                  ws.newNode = new InternalNode();
                  ws.offset = 1;
                  resetChildren(q, ws.scratch, 0, 2);
                  resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
               }
            }
            else if (ws.guideChanged) {
               ws.guideChanged = resetGuide(q);
            }
         }

         return ws;
      }
   }


   static int copyOutChildren(InternalNode q, Node[] x) {
   // copy children of q into x, and return # of children

      int sz = 2;
      x[0] = q.child0; x[1] = q.child1;
      if (q.child2 != null) {
         x[2] = q.child2; 
         sz = 3;
      }
      return sz;
   }

   static void insertNode(Node[] x, Node p, int sz, int pos) {
   // insert p in x[0..sz) at position pos,
   // moving existing extries to the right

      for (int i = sz; i > pos; i--)
         x[i] = x[i-1];

      x[pos] = p;
   }

   static boolean resetGuide(InternalNode q) {
   // reset q.guide, and return true if it changes.

      String oldGuide = q.guide;
      if (q.child2 != null)
         q.guide = q.child2.guide;
      else
         q.guide = q.child1.guide;

      return q.guide != oldGuide;
   }


   static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
   // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
   // also resets guide, and returns the result of that

      q.child0 = x[pos]; 
      q.child1 = x[pos+1];

      if (sz == 3) 
         q.child2 = x[pos+2];
      else
         q.child2 = null;

      return resetGuide(q);
   }

static void PrintRange(Node p, String planet0, String planet1, int height, BufferedWriter out) throws Exception {

	
    if (height == 0) //node is leaf, downcast
    {
    	LeafNode q = (LeafNode) p;
    
    	//if queried min planet is less than guide of node
    	//&& max planet is greater than guide of node
    	//print node
        if ((planet0.compareTo(q.guide) <= 0) && (planet1.compareTo(q.guide) >= 0))
        {
            
        	String str = q.guide + " " + q.value;
        	out.write(str);
        	out.newLine();
        }
    }

    else
    {
    	InternalNode q = (InternalNode) p;
    	
    	PrintRange(q.child0, planet0, planet1, height-1, out);
    	PrintRange(q.child1, planet0, planet1, height-1, out);
   
    	if (q.child2 != null)
    	{
    		PrintRange(q.child2, planet0, planet1, height-1, out);
    	}
    }
    

}//PrintRange
    

static void PrintLE(Node p, String planet1, int height, BufferedWriter out) throws Exception {

	//node is leaf, downcast
    if (height == 0)
    {
        LeafNode q = (LeafNode) p;
    	if (q.guide.compareTo(planet1) <= 0)    
        {
            String str = q.guide + " " + q.value;
    		out.write(str);
    		out.newLine();
        }
    }

    //node is internal node, downcast
    else
    {	
    	InternalNode q = (InternalNode) p;
   
    	//queried max planet is less than guide of first child
    	if (planet1.compareTo(q.child0.guide) <= 0)
    	{
    		PrintLE(q.child0, planet1, height-1, out);
    	}

    	//node has no third child
    	//OR queried max planet is less than guide of third planet
    	else if (q.child2 == null || planet1.compareTo(q.child1.guide) <= 0)
    	{
    		//PrintAll(q.child0, height-1, out);
    		PrintLE(q.child1, planet1, height-1, out);
    	}

    	//queried max planet is greater than guide of first and second child
    	else
    	{
    		//PrintAll(q.child0, height-1, out);
    		//PrintAll(q.child1, height-1, out);
    		PrintLE(q.child2, planet1, height-1, out);
    	}
    }
    
}//PrintLE

    
static void PrintGE(Node p, String planet0, int height, BufferedWriter out) throws Exception {

	//node is leaf, downcast
    if (height == 0)
    {
    	LeafNode q = (LeafNode) p;
    
        if (q.guide.compareTo(planet0) >= 0)
        {
            String str = q.guide + " " + q.value;
            out.write(str);
            out.newLine();
        }
    }

    //node is internal node, downcast
    else
    {	
    	InternalNode q = (InternalNode) p;
    	
    	//if queried min planet is less than guide of first child
    	//go down first child
    	if (planet0.compareTo(q.child0.guide) <= 0)
    	{
    		PrintGE(q.child0, planet0, height-1, out);
    	}
    	
    	
    	//if no third child
    	//OR queried min planet is less than guide of second child
    	//go down second child
    	else if (q.child2 == null || planet0.compareTo(q.child1.guide) <= 0)
    	{
    		PrintGE(q.child1, planet0, height-1, out);
    	}

    	//queried min planet is in third child
    	else
    	{
    		PrintGE(q.child2,  planet0,  height-1,  out);
    	}
    }
    
}//PrintGE


static void PrintAll(Node p, int height, BufferedWriter out) throws Exception {
    
	//node is leaf, downcast
	if (height == 0)
    {
        LeafNode q = (LeafNode) p;
    	
        String str = q.guide + " " + q.value;
        out.write(str);
        out.newLine();
    }

	//node is internal node, downcast
    else if (height > 0)
    {
    	InternalNode q = (InternalNode) p;
    	
        PrintAll(q.child0, height-1, out);
        PrintAll(q.child1, height-1, out);
        
        if (q.child2 != null)
        {
            PrintAll(q.child2, height-1, out);
        }
    }
    
}//PrintAll
}//class twothree