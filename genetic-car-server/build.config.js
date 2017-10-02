/**
 * This file contains all configuration for the build process.
 */
module.exports = {
    app_src_path: "src/main/webapp",
    package_path: "src/main/resources/public",
    tmp_dir: '.tmp',

    app_files: {
        js: [
            'src/main/webapp/scripts/app.js',
            'src/main/webapp/scripts/app/**/*.js',
            'src/main/webapp/scripts/common/**/*.js',
            '!src/main/webapp/scripts/**/*_test.js'
        ],
        jsunit: [
            'src/main/webapp/scripts/app/**/*_test.js',
            'src/main/webapp/scripts/common/**/*_test.js'],

        tpl: [
            'src/main/webapp/scripts/app/**/*-tpl.html',
            'src/main/webapp/scripts/common/**/*-tpl.html'
        ],

        index: ['src/main/webapp/index.html'],
        less: [
            'src/main/webapp/styles-less/**/*.less',
            'src/main/webapp/scripts/common/**/*.less',
            'src/main/webapp/scripts/app/**/*.less'
        ]
    },

    /**
     * This is a collection of files used during testing only.
     */
    test_files: {
        js: [
            'node_modules/angular-mocks/angular-mocks.js'
        ]
    },

    vendor_files: {
        js: [
            'node_modules/angular/angular.js',
            'node_modules/angular-resource/angular-resource.js',
            'node_modules/angular-sanitize/angular-sanitize.js',
            'node_modules/angular-ui-router/release/angular-ui-router.js',
            'node_modules/angular-ui-bootstrap/dist/ui-bootstrap.js',
            'node_modules/lodash/lodash.js'
        ],
        css: [
            'node_modules/bootstrap/dist/css/bootstrap.min.css'
        ],
        assets: []
    }
};