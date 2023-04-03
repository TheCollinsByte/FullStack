import './App.css';
import logo from '../logo.png';

import Header from './Header';
import AppContent from './AppContent';

function App() {
  return (
    <div>
      <Header pageTitle="Frontend authenticated With JWT" logoSrc={logo} />

      <div className='container-fluid'>
        <div className='row'>
          <div className='col'>
            <AppContent />
          </div>
        </div>
      </div>

    </div>
  );
}

export default App;
