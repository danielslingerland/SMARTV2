package tests;

import java.util.Arrays;

import junit.framework.TestCase;
import smrt2.Experiment;
import smrt2.Model;
import smrt2.Solver;


public class ExperimentTest extends TestCase {
	private Model m;
	private Experiment e;
	
	public void testExperimentCreation() {
		m = new Model();
		e = new Experiment(m);
		assertNotNull(e);
	}
	
	
	public void testGetValueFromParameter() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos=e.getParameterPosition("k");
		double got = e.getParameterValue(pos);
		assertEquals(1.0, got);
	}
	
	public void testChangeParameterValue() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos = e.getParameterPosition("k");
		e.setParameterValue(pos, 1.96);
		double got = e.getParameterValue((e.getParameterPosition("k")));
		assertEquals(1.96, got);
	}
	public void testGetInitialFromState() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos=e.getStatePosition("A");
		double got = e.getStateValue(pos);
		assertEquals(1.0, got);
	}
	
	public void testChangeInitialStateValue() throws Exception {
		m = new Model();
		m.addOde("A", "k");
		e = new Experiment(m);
		int pos = e.getStatePosition("A");
		e.setStateValue(pos, 25.09);
		double got = e.getStateValue((e.getStatePosition("A")));
		assertEquals(25.09, got);
	}
	
	public void testReconstuctFormulas(){
		Model m = new Model("Name");
		m.addOde("A", "k1*((A+B)+k1)");
		m.addOde("B", "B-A+k2");
		e = new Experiment(m);
		
		String[] actual = e.reconstructFormulas();
		String[] expected = {"P[0]*((S[1]+S[2])+P[0])", "S[2]-S[1]+P[1]"};
		assertTrue(Arrays.equals(expected, actual));
	}
	
	public void testSolverCreation() {
		Model mo = new Model("Name");
		mo.addOde("A", "k1");
		mo.addOde("B", "k2*A");
		Experiment ex = new Experiment(mo);
		ex.setParameterValue(0, 1);
		ex.setParameterValue(1, 2);
		ex.setStateValue(0, 0);
		ex.setStateValue(1, 0);
		ex.setTimeFrame(0.0, 10.0, 1.0);
		
		double[][] expected = {{0, 0, 0 }, {1, 1, 0}, {2, 2, 2}, {3, 3, 6}, {4, 4, 12}, {5, 5, 20}, {6, 6, 30},
				{7, 7, 42}, {8, 8, 56}, {9, 9, 72}, {10, 10, 90}};
		double[][] actual = ex.run();
		
		for (int i = 0; i < actual.length; i++) {
			for (int j = 0; j < actual[i].length; j++) {
				System.out.printf("%s = %s | ", expected[i][j], actual[i][j]);
				assertEquals(expected[i][j], actual[i][j]);
			}
		System.out.println();
		}
	}
}
