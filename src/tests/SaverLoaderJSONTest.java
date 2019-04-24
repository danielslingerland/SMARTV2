package tests;

import java.util.Arrays;
import junit.framework.TestCase;
import smrt2.Experiment;
import smrt2.Model;
import smrt2.Ode;
import smrt2.SaverLoader;

public class SaverLoaderJSONTest extends TestCase {

	public void testCreateSaverLoaderJSON() {
		SaverLoader mySl= SaverLoader.getInstance();
		assertNotNull(mySl);
	}
	
	public void testSaveLoadOde() {
		String savePath = "./data/test_Json.json";
		Ode expectedOde = new Ode("F","H*R");
		SaverLoader mySl= SaverLoader.getInstance();
		mySl.save(savePath, expectedOde);
		
		Ode observedOde =  (Ode) mySl.load(savePath, Ode.class);
		assertEquals(expectedOde.getState(), observedOde.getState());
		assertEquals(expectedOde.getFormula(), observedOde.getFormula());		
	}
	
	public void testSaveLoadModel() {
		String savePath = "./data/test_JsonModel.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		m.addParameter("L");
		
		SaverLoader mySl = SaverLoader.getInstance();
		mySl.save(savePath, m);
		
		Model mObserved = (Model) mySl.load(savePath, Model.class);
		assertTrue(m.getStates().equals(mObserved.getStates()));
	}
	
	
	public void testSaveLoadExperiment() {
		String savePath = "./data/test_JsonExperiment.json";
		Model m = new Model("Test JSON model");
		m.addOde("H", "x + 4k2");
		m.addOde("B", "r^2 * 5");
		m.addParameter("L");
		
		Experiment e = new Experiment(m, "Test experiment for JSON saving");
		
		SaverLoader mySl = SaverLoader.getInstance();
		
		mySl.save(savePath, e);
		
		Experiment eObs = (Experiment) mySl.load(savePath, Experiment.class);
		
		assertTrue(Arrays.equals(e.getTimeValues(), eObs.getTimeValues()));
		assertTrue(e.getName().equals(eObs.getName()));
		assertTrue(Arrays.equals(e.getStateNames(), eObs.getStateNames()));
		assertTrue(Arrays.equals(e.getParameterValues(), eObs.getParameterValues()));
		assertTrue(Arrays.equals(e.getStateValues(), eObs.getStateValues()));
	}
}
