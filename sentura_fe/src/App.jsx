import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [countries, setCountries] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCountry, setSelectedCountry] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/countries")
      .then(res => res.json())
      .then(data => setCountries(data))
      .catch(err => console.error("Error fetching data:", err));
  }, []);

  const filteredCountries = countries.filter(c =>
    c.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="App">
      <h1>Country Explorer</h1>


      <input
        type="text"
        placeholder="Search countries..."
        className="search-box"
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <table>
        <thead>
          <tr>
            <th>Flag</th>
            <th>Name</th>
            <th>Capital</th>
            <th>Region</th>
            <th>Population</th>
          </tr>
        </thead>
        <tbody>
          {filteredCountries.map((country, index) => (
            <tr key={index} onClick={() => setSelectedCountry(country)} style={{ cursor: 'pointer' }}>
              <td><img src={country.flag} alt="flag" width="50" /></td>
              <td>{country.name}</td>
              <td>{country.capital}</td>
              <td>{country.region}</td>
              <td>{country.population.toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {selectedCountry && (
        <div className="modal-overlay" onClick={() => setSelectedCountry(null)}>
          <div className="modal-content" onClick={e => e.stopPropagation()}>
            <h2>{selectedCountry.name} Details</h2>
            <img src={selectedCountry.flag} alt="flag" width="150" />
            <p><strong>Capital:</strong> {selectedCountry.capital}</p>
            <p><strong>Region:</strong> {selectedCountry.region}</p>
            <p><strong>Population:</strong> {selectedCountry.population.toLocaleString()}</p>
            <button onClick={() => setSelectedCountry(null)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;