
package scalaSci.Wizards

object  LinearSystemsWizard {

  def runWizard = {

    
    
    
  import java.awt._
  import javax.swing._
  import java.awt.event._
  import scalaExec.Interpreter.GlobalValues
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rtextarea.RTextScrollPane


val  title = "Linear Systems Equations Wizard"

val  lsw = new JFrame(title)

    // general scalaSci matrix 
def prepareGeneralPanel() = {
 val gpanel = new JPanel // "General" panel
 gpanel.setLayout(new GridLayout(2,1))
 val jtxtpane = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
 jtxtpane.setFont(new Font(GlobalValues.paneFontName, Font.PLAIN, GlobalValues.paneFontSize))
 jtxtpane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA)
 jtxtpane.setCodeFoldingEnabled(true)
 
 val  generalSystemButton = new JButton("General Linear MXN system, A x = b")
 generalSystemButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
      jtxtpane.setText("""  
// general solution, A is any scalaSci matrix, b is an one-dimensional double array
var solution = A.solve(b)

""")
   }
}  // ActionListener
 )

val  buttonPanel = new JPanel()
val topLayout = new GridLayout(2, 1)
buttonPanel.setLayout(topLayout)
buttonPanel.add(generalSystemButton)

val jscroll = new JScrollPane(jtxtpane)

gpanel.add(buttonPanel)
gpanel.add(jscroll)

 gpanel
}   // prepareGeneralPanel


def prepareApacheCommonsPanel() = {
 
 val jtxtpane = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
 jtxtpane.setFont(new Font(GlobalValues.paneFontName, Font.PLAIN, GlobalValues.paneFontSize))
      
 jtxtpane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA)
 jtxtpane.setCodeFoldingEnabled(true)
        
 val  LUDecompositionButton = new JButton("LU Decomposition")
 LUDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""

import  org.apache.commons.math4.linear.LUDecomposition 

import  org.apache.commons.math4.linear.Array2DRowRealMatrix

var c = 2.3                             
var a = $$(4.5, 2*c, c, null, 0.34, 0.44, -0.56, null,  0.33, -1.2, 0.45)  // whatever your matrix is
var rmA = new Array2DRowRealMatrix(a)  // transform it to an Apache Commons Array2DRealMatrix

var luObj = new LUDecomposition(rmA)   // perform an LU decomposition on A

var  luObjU = new RDDA(luObj.getU.getData)   // get U part in a RichDouble2DArray
var  luObjL = new RDDA(luObj.getL.getData)  // get L part in a RichDouble2DArray

// solve for  one  right-hand side
c = 3.4                             
var b = $$(sqrt(c), c, null,  5.6, -2.1, null, c-2.3, 7.8)
var bm = new Array2DRowRealMatrix(b)

var solver = luObj.getSolver
var sol = solver.solve(bm).getData

var shouldBeZero = a*sol-b    // verify the solution



// solve for   many  right-hand sides
c = -1.2                             
var b2 = $$(c, 3.4, 5.6, null, -2.1, -2.3, 0.12)
b2 = b2~   // take  right-hand sides as column vectors
var bm2 = new Array2DRowRealMatrix(b2)

var sol2 = solver.solve(bm2).getData

var shouldBeZero2 = a*sol2-b2    // verify the solution




  """    )
       }
     }
   )   // LUDecompositionButton



 val  QRDecompositionButton = new JButton("QR Decomposition")
 QRDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  

import  org.apache.commons.math4.linear.QRDecomposition 
import  org.apache.commons.math4.linear.Array2DRowRealMatrix

var a = $$(4.5, 3.4, 2.3, null, 0.34, 0.44, -0.56, null,  0.33, -1.2, 0.45)  // whatever your matrix is
var rmA = new Array2DRowRealMatrix(a)  // transform it to an Apache Commons Array2DRealMatrix

var qrObj = new QRDecomposition(rmA)   // perform a QR decomposition on A

var  qrObjQ = new RDDA(qrObj.getQ.getData)   // get Q part in a RichDouble2DArray
var  qrObjR = new RDDA(qrObj.getR.getData)  // get R part in a RichDouble2DArray


var shouldBeZero = qrObjQ*qrObjR-a   // this should be zero according to the QR decomposition

    """
       ) 
     }
   }  // ActionListenetr
 ) 

 

 val  CholeskyDecompositionButton = new JButton("Cholesky  Decomposition")
 CholeskyDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  

  

import  org.apache.commons.math4.linear.CholeskyDecomposition 
import  org.apache.commons.math4.linear.Array2DRowRealMatrix

var N=4
var a = rand(N, N)  // a random matrix
  // make it symmetric
for (r<-0 until N)  
  for (k<-r until N)
      a(r, k)  = a(k, r)

  a = a*a~   // make it positive definite     

var rmA = new Array2DRowRealMatrix(a)  // transform it to an Apache Commons Array2DRealMatrix

var choleskyObj = new CholeskyDecomposition(rmA)   // perform a QR decomposition on A

var dtA = choleskyObj.getDeterminant     // return the determinant of the matrix
var lA = new RDDA(choleskyObj.getL.getData)  // get the matrix  L of the decomposition as a RDDA

    
    """
       ) 
     }
   }  // ActionListenetr
 ) 


 val  SingularValueDecompositionButton = new JButton("Singular Value Decomposition")
 SingularValueDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  

import  org.apache.commons.math4.linear.SingularValueDecomposition
import  org.apache.commons.math4.linear.Array2DRowRealMatrix
import  org.apache.commons.math4.linear.ArrayRealVector

var a = $$(1.0,  -1.0,  -1.0,   -1,   -1, null,   -1, 2,  0,   0,   0, null,   -1,  0,  3,  1, 1, null,    -1,  0, 1,  4,  2, null,     -1,  0,  1,  2, 5) // whatever your matrix is

var rmA = new Array2DRowRealMatrix(a)  // transform it to an Apache Commons Array2DRealMatrix

var svdObj = new SingularValueDecomposition(rmA)   // perform an SVD decomposition on A

var  aU = svdObj.getU.getData  // get U matrix as an Array[Array[Double]]
var  aS = svdObj.getS.getData    // get S, matrix of singular values
var aV = svdObj.getV.getData  // get V matrix

var shouldBeZero = aU * aS * (aV~) - a   // verify that the SVD equation holds

var ai = svdObj.getConditionNumber  // return the condition number of the matrix

var an = svdObj.getNorm  // get the L_{2} norm of the matrix

var minSingularValue = -1.0 // below which singular values are ignored, a 0 or negative value implies all singular values will be used
var covariance = svdObj.getCovariance(minSingularValue)
 
  // Get a solver for finding the A  \cdot X = B solution in least square sense.
var solver = svdObj.getSolver  

var b = AD("4.5 0.11 -1.1 0.2 -0.94 ")
var bv = new ArrayRealVector(b)

var x = solver.solve(bv).toArray
var shouldBeZeroAlso = a*x-b
    """ ) 
     }
   }  // ActionListenetr
 ) 

val  buttonPanel = new JPanel()
val topLayout = new GridLayout(2, 2)
buttonPanel.setLayout(topLayout)
buttonPanel.add(LUDecompositionButton)
buttonPanel.add(QRDecompositionButton)
buttonPanel.add(CholeskyDecompositionButton)
buttonPanel.add(SingularValueDecompositionButton)
val jscroll = new JScrollPane(jtxtpane)

val MTJpanel = new JPanel // "MTJ" panel
 MTJpanel.setLayout(new GridLayout(2,1))
 MTJpanel.add(buttonPanel)

MTJpanel.add(jscroll)

 MTJpanel

}

 def prepareMTJPanel() = {
 
 val jtxtpane = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
 jtxtpane.setFont(new Font(GlobalValues.paneFontName, Font.PLAIN, GlobalValues.paneFontSize))
      
 jtxtpane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA)
 jtxtpane.setCodeFoldingEnabled(true)
        
 val  LUDecompositionButton = new JButton("LU Decomposition")
 LUDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""
import  no.uib.cipr.matrix

import  no.uib.cipr.matrix.DenseMatrix
import  no.uib.cipr.matrix.DenseLU

var a = $$(4.5, 3.4, 2.3, null, 0.34, 0.44, -0.56, null,  0.33, -1.2, 0.45)  // whatever your matrix is
var rmA = new DenseMatrix(a)  // transform it to the MTJ DenseMatrix
val nr = rmA.numRows
val nc = rmA.numColumns

var dlu = DenseLU.factorize(rmA)  // creates an  LU decomposition

var lpart = dlu.getL  // lower part
var upart = dlu.getU // upper part

// solve for  one  right-hand side
var b = $$(3.4, null,  5.6, null, -2.1)
var db = new DenseMatrix(b)
var x = dlu.solve(db)

// verify the solution
var shoulBeZero = a*x.toDoubleArray-b

  """    )
       }
     }
   )   // LUDecompositionButton



 val  QRDecompositionButton = new JButton("QR Decomposition")
 QRDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  

import  no.uib.cipr.matrix

import  no.uib.cipr.matrix.DenseMatrix
import  no.uib.cipr.matrix.QR

var a = $$(4.5, 3.4, 2.3, null, 0.34, 0.44, -0.56, null, 0.33, -1.2, 0.45)  // whatever your matrix is
var rmA = new DenseMatrix(a)  // transform it to an MTJ Dense Matrix
val nr = rmA.numRows
val nc = rmA.numColumns

var dqr = QR.factorize(rmA)  // creates a  QR decomposition

var rpart = dqr.getR  // R part
//var rpartM = new scalaSci.MTJ.Mat(rpart)
var qpart = dqr.getQ // Q part
//var qpartM = new scalaSci.MTJ.Mat(qpart)  // transform to Mat



    """
       ) 
     }
   }  // ActionListenetr
 ) 

 

 val  CholeskyDecompositionButton = new JButton("Cholesky  Decomposition")
 CholeskyDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  
  

import  org.apache.commons.math4.linear.CholeskyDecomposition 
import  org.apache.commons.math4.linear.Array2DRowRealMatrix

var a = $$(1.0,  -1.0,  -1.0,   -1,   -1, null,  -1, 2,  0,   0,   0, null,  -1,  0,  3,  1, 1, null,  -1,  0, 1,  4,  2, null,    -1,  0,  1,  2, 5) // whatever your matrix is

var rmA = new Array2DRowRealMatrix(a)  // transform it to an Apache Commons Array2DRealMatrix

var choleskyObj = new CholeskyDecomposition(rmA)   // perform a QR decomposition on A

var dtA = choleskyObj.getDeterminant     // return the determinant of the matrix
var lA = new RDDA(choleskyObj.getL.getData)  // get the matrix  L of the decomposition as a RDDA

    
    """
       ) 
     }
   }  // ActionListenetr
 ) 


 val  SingularValueDecompositionButton = new JButton("Singular Value Decomposition")
 SingularValueDecompositionButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""  

    """ ) 
     }
   }  // ActionListenetr
 ) 
  
val  buttonPanel = new JPanel()
val topLayout = new GridLayout(2, 2)
buttonPanel.setLayout(topLayout)
buttonPanel.add(LUDecompositionButton)
buttonPanel.add(QRDecompositionButton)
buttonPanel.add(CholeskyDecompositionButton)
buttonPanel.add(SingularValueDecompositionButton)
val jscroll = new JScrollPane(jtxtpane)

val acpanel = new JPanel // "Apache Commons" panel
 acpanel.setLayout(new GridLayout(2,1))
 
acpanel.add(buttonPanel)

acpanel.add(jscroll)

 acpanel
}   // prepareApacheCommonsPanel



def prepareNRPanel() = {
 val gpanel = new JPanel // "Numerical Recipes" panel
 gpanel.setLayout(new GridLayout(2,1))
 val jtxtpane = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
 jtxtpane.setFont(new Font(GlobalValues.paneFontName, Font.PLAIN, GlobalValues.paneFontSize))
      
 jtxtpane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA)
 jtxtpane.setCodeFoldingEnabled(true)
 
 val  GSButton = new JButton("Gauss-Jordan")
 GSButton.addActionListener( new ActionListener {
  def actionPerformed( e: ActionEvent ) {
       jtxtpane.setText("""
         import com.nr.la.GaussJordan._
        /* Linear equation solution by Gauss-Jordan elimination.
    The input matrix is a[0..n-1][0..n-1].
     b[0..n-1][0..m-1] is
    input containing the m right-hand side vectors. On output,
    a is replaced by its matrix inverse, and b is replaced by
    the corresponding set of solution vectors.
   */
       var a = $$(2.3, 0.2, 0.4, 0.45, null, 1.1, -1.1, 0.34, 0.2, null, 0.34, 4.5, 2.3, 1.1, null, 0.1, 0.2, 0.55, 3.4)  // the matrix
       var b = $$(1.23, 5.6, -0.23, 0.45, null,  0.34, 0.24, -0.45, 0.03)    // the right-hand side vectors
       b = b~   // transpose since we require column vectors
       var ac = a.clone  // make a copy of the a matrix since gaussj writes over the original
       var bc = b.clone // make a copy of the b matrix since gaussj writes over the original
       gaussj(a, b)  // call Numerical Recipes Gauss-Jordan routine
       var souldBeIdentity  = ac*a  // since a is replaced by its matrix inverse
       var shouldBeZero = ac*b-bc   // since b is the solution  
         
   """)
   }
})

 val  LUDecompButton = new JButton("LU-Decomposition") 
  LUDecompButton.addActionListener( new ActionListener {
     def actionPerformed( e: ActionEvent ) {
     	  jtxtpane.setText("""
    
    import com.nr.la.LUdcmp
    	   /*
    Given a matrix a[0..n-1][0..n-1], this routine replaces it by the LU decomposition of a
    rowwise permutation of itself. a is input. On output, it is arranged as in equation (2.3.14)
    above; indx[0..n-1] is an output vector that records the row permutation effected by the
    partial pivoting; d is output as +/-1 depending on whether the number of row interchanges
    was even or odd, respectively. This routine is used in combination with solve to solve linear
    equations or invert a matrix.
    */
     var  a = $$(2.3, -0.6, 0.45, null,  -2.3, 0.2, -0.03, null, 0.2, 0.55, -0.2)  // whatever your matrix
     var LUobj = new  LUdcmp(a)  // perform LU decomposition
     var  b = AD("5 6.7 7")   // a right-hand side
     var x = new AD(3)  // create an array to hold the  solution
     LUobj.solve(b, x)    // solve for one right-hand size
     var shouldBeZero = a*x - b

     // solve now for multiple right-hand sides
     var bb = $$(0.11, -0.2,  1.2, null, -1.2, 2.3, -0.22)
     bb = bb~ //  transpose  to  treat a s column vector
     var xx = new RDDA(size(bb))  // a matrix to keep the solution, the same size as bb
     LUobj.solve(bb, xx)    // solve for muliple right-hand sides
     var shouldAlsoBeZero = a*xx - bb
     """)
      }
  })

 val   SVDDecompButton = new JButton("SVD-Decomposition") 
  SVDDecompButton.addActionListener( new ActionListener {
     def actionPerformed( e: ActionEvent ) {
     	  jtxtpane.setText("""

    import com.nr.la.SVD

     var  a = $$(2.3, -0.6, 0.45, null,  -2.3, 0.2, -0.03, null, 0.2, 0.55, -0.2)  // whatever your matrix
     var SVDobj = new  SVD(a)  // perform SVD decomposition
     var  b = AD("5 6.7 7")   // a right-hand side
     var x = new AD(3)  // create an array to hold the  solution
     SVDobj.solve(b, x)    // solve for one right-hand size
     var shouldBeZero = a*x - b

     // solve now for multiple right-hand sides
     var bb = $$(0.11, -0.2,  1.2, null, -1.2, 2.3, -0.22)
     bb = bb~ //  transpose  to  treat a s column vector
     var xx = new RDDA(size(bb))  // a matrix to keep the solution, the same size as bb
     SVDobj.solve(bb, xx)    // solve for muliple right-hand sides
     var shouldAlsoBeZero = a*xx - bb
     
     var rankOfA = SVDobj.rank  // get the matrix's rank
     
     var nullityOfA = SVDobj.nullity  // get the matrix's nullity
     
     // Give an orthonormal basis for the range of A as the columns of the returned matrix
     var  rangeOfA = SVDobj.range


     """)
      }
  })
  

val  buttonPanel = new JPanel()
val topLayout = new GridLayout(2, 2)
buttonPanel.setLayout(topLayout)
buttonPanel.add(GSButton); 
buttonPanel.add(LUDecompButton)
buttonPanel.add(SVDDecompButton)

val jscroll = new JScrollPane(jtxtpane)

gpanel.add(buttonPanel)
gpanel.add(jscroll)

 gpanel
}


val tabsPane = new JTabbedPane

val gpanel = prepareGeneralPanel
val ACpanel = prepareApacheCommonsPanel
tabsPane.addTab("Apache Commons", ACpanel)
tabsPane.addTab("General", gpanel)
val NRpanel =  prepareNRPanel
tabsPane.addTab("Numerical Recipes", NRpanel)
tabsPane.addTab("MTJ", prepareMTJPanel)


lsw.add(tabsPane)
lsw.setSize(700, 900)
lsw.setLocation(10, 10)
lsw.setVisible(true)

     }
    }