from setuptools import setup
import setuptools
from setuptools.command.test import test as TestCommand
import sys
import os


class PyTest(TestCommand):
    test_package_name = 'src'

    def finalize_options(self):
        TestCommand.finalize_options(self)
        _test_args = [
            '--ignore=build',
            '--ignore=env',
            '--cov={0}'.format(self.test_package_name),
            '--cov-report=term-missing',
            '--cov-config=.coveragerc',
            '--pep8',
            '--cov-report=html'
        ]
        extra_args = os.environ.get('PYTEST_EXTRA_ARGS')
        if extra_args is not None:
            _test_args.extend(extra_args.split())
        self.test_args = _test_args
        self.test_suite = True

    def run_tests(self):
        import pytest
        errno = pytest.main(self.test_args)
        sys.exit(errno)

setup(
    name='genetic-car-starter-python',
    version='0.0.1',
    description='Ce projet est est un exercice permettant de se familiariser avec les algorithmes genetiques de maniere ludique.',
    url='',
    author='rdelassus',
    author_email='remi.delassus@gmail.com',
    license='',
    packages=setuptools.find_packages(),
    install_requires=[
        'httplib2',
        'ujson'
    ],
    zip_safe=False,
    tests_require=[
        'pytest-cov',
        'pytest-pep8',
        'pytest-cache',
        'pytest-gitignore',
        'pytest',
        'jsonschema'
    ],
    cmdclass={'test': PyTest},
)
