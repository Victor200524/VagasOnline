// src/app/components/Header/index.jsx
import Link from 'next/link';
import styles from '../../styles/Header.module.css';

export default function Header() {
  return (
    <header className={styles.header}>
      <nav className={styles.nav}>
        <Link href="/" className={styles.logo}>
          VagasOnline
        </Link>
        <div className={styles.links}>
          <Link href="/" className={styles.navLink}>
            Ver Vagas
          </Link>
          <Link href="/cadastro" className={`${styles.navLink} ${styles.ctaButton}`}>
            Cadastrar Vaga
          </Link>
        </div>
      </nav>
    </header>
  );
}