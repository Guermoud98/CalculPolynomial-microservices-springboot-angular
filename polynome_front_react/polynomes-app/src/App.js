import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [polynomes, setPolynomes] = useState([]);
  const [polynomeInput, setPolynomeInput] = useState('');
  const [root, setRoot] = useState('');
  const [factorized, setFactorized] = useState('');
  const [showForm, setShowForm] = useState(false);

  
  const fetchPolynomes = () => {
    axios.get('http://localhost:8082/polynomes/all') 
      .then(response => {
        setPolynomes(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the polynomes:', error);
      });
  };

  useEffect(() => {
    fetchPolynomes();
  }, []); 

 
  const handleAddPolynome = () => {
    setShowForm(true);
  };


  const handleInputChange = (event) => {
    setPolynomeInput(event.target.value);
  };


  const handleCalculateRoots = () => {
    axios.post('http://localhost:8083/racines/calculer', { expression: polynomeInput })
      .then(response => {
        setRoot(response.data);
      })
      .catch(error => {
        console.error('There was an error calculating the roots:', error);
      });
  };

  const handleFactorize = () => {
    axios.post(`http://localhost:8084/api/factorisation/factorize?polynome=${encodeURIComponent(polynomeInput)}`)
      .then(response => {
        setFactorized(response.data);
      })
      .catch(error => {
        console.error('There was an error factorizing the polynomial:', error);
      });
  };

  
  const handleBackClick = () => {
    setPolynomeInput('');
    setRoot('');
    setFactorized('');
    setShowForm(false);
    fetchPolynomes();
  };

  return (
    
    <div className="App">
      <h1>Polynome App</h1>
      
      {!showForm && (
        <div>
          <h2>Polynomials List</h2>
          <table className="polynomial-table">
            <thead>
              <tr>
                
                <th>Polynomial Expression</th>
              </tr>
            </thead>
            <tbody>
              {polynomes.length === 0 ? (
                <tr>
                  <td colSpan="2">No polynomials available</td>
                </tr>
              ) : (
                polynomes.map((polynome) => (
                  <tr key={polynome.id}>
                    
                    <td>{polynome.expression}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
          <button className="add-polynomial-btn" onClick={handleAddPolynome}>Calculate Polynomial</button>
        </div>
      )}

      {showForm && (
        <div className="form-container">
          <h2>Enter Polynomial Expression</h2>
          <input
            type="text"
            value={polynomeInput}
            onChange={handleInputChange}
            placeholder="Enter polynomial"
            className="polynomeInput"
          />
          <br/>
          <br/>
          <br/>
          <div className="button-group">
            <button className="calc-btn" onClick={handleCalculateRoots}>Calculate Roots</button>
            <button className="factorize-btn" onClick={handleFactorize}>Factorize</button>
          </div>

          {root && <div className="result"><h3>Roots: {root}</h3></div>}
          {factorized && <div className="result"><h3>Factorized Form: {factorized}</h3></div>}

          <br/>
          <br/>
          <button className="back-btn" onClick={handleBackClick}>Back</button>
        </div>
      )}
    </div>
   
  );
}

export default App;
