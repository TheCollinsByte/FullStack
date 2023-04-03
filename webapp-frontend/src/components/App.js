import './App.css';
import logo from '../logo.png';

import Header from './Header';

function App() {
  return (
    <div>
      <Header pageTitle="Frontend authenticated With JWT" logoSrc={logo} />
    </div>
  );
}

export default App;
