export const Footer = () => {
  return (
    <footer className='fixed z-50 bg-background w-full bottom-0 border-t flex flex-col md:flex-row justify-between px-12 py-4 gap-y-1.5 transition-colors'>
      <p className='text-xs max-md:w-full text-center'>
        Copyright &copy; 2024 ChatLog. Alle Rechte vorbehalten.
      </p>
      <p className='text-xs max-md:w-full text-center'>
        <span className='underline text-sky-600 dark:text-sky-400'>
          Impressum
        </span>
        {' | '}
        <span className='underline text-sky-600 dark:text-sky-400'>
          Datenschutz
        </span>
        {' | '}
        <span className='underline text-sky-600 dark:text-sky-400'>
          Nutzungsbedingungen
        </span>
      </p>
    </footer>
  );
};
