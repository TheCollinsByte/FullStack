import * as React from 'react';

export default function Header(props) {

    return (
        <header>
            <img src={props.logoSrc} className="App-logo" alt='Logo' />
            <h1>{props.pageTitle}</h1>
        </header>
    );
}