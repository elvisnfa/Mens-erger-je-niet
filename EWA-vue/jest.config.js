module.exports = {
    // transformIgnorePatterns: [
    //     "/node_modules/(?!bootstrap)/"  // Ignore transformation for all node_modules except bootstrap
    // ],
    testEnvironment: 'jest-environment-jsdom',
    "setupFilesAfterEnv": [
        "jest-expect-message"
    ],
    "testMatch": [
        "**/src/**/*.spec.js"
    ],
    "moduleNameMapper": {
        "^@/(.*)$": "<rootDir>/src/$1",
        "/images/(.*)$": "<rootDir>/public/images/$1",
        "\\.(css|less|scss|sass)$": "identity-obj-proxy"
    },
    "transform": {
        "^.+\\.js$": "babel-jest",
        "^.+\\.vue$": "@vue/vue3-jest",
        "^.+\\.(jpg|jpeg|png|gif|svg|mp3|mp4|mpeg)$": "<rootDir>/jest-media-transformer.js"
    }
};