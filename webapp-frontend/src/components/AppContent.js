import * as React from "react";
import WelcomeContent from "./WelcomeContent";
import AuthContent from "./AuthContent";
import LoginForm from "./LoginForm";

export default class AppContent extends React.Component {
    render() {
        return (
            <div>
                <WelcomeContent />
                <AuthContent />
                <LoginForm />
            </div>
        )
    };
}